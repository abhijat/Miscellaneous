import urllib2,json
import time,os
import pymongo

class JSONDataBase:
    def __init__(self, db_name):
        self.conn = pymongo.Connection()
        self.db = self.conn[db_name]
        self.coll = self.db['top_level_links']
        self.log = open(db_name+'.txt','w')
        self.page = self.conn[db_name]['last_good_page']
        return
        
    def write(self,obj):
        self.coll.insert(obj)
        self.log.write(str(obj))
        
    def close(self):
        self.log.close()
        
    def get_last_page(self):
        self.cur_page = self.page.find_one()
        if 'page_id' in self.cur_page:
            return self.cur_page['page_id']
        else:
            return ''
    
    def update_page(self, new_page):
        last = self.get_last_page()
        self.page.update(
            {'page_id':last}, 
            {'page_id':new_page}
        )
        return
    
def fetch_reddit_json(curr_depth,db,next='',max_depth=99999):
    url = 'http://www.reddit.com/.json'
    headers = {
        'User-Agent' : 'Search engine project by /u/fjellfras'
    }
    
    print '''
    Called with arguments: next link %s, current queue depth %d
    ''' % (next, curr_depth)
    if curr_depth == max_depth:
        return
    else:
        curr_depth += 1
        if next:
            url += '?after=' + next
        request = urllib2.Request(url,headers=headers)
        raw_str = urllib2.urlopen(request).read()
        
        top_dict = json.loads(raw_str)
        next = top_dict['data']['after']
        if not next:
            # Bail out right now instead of corrupting the database
            return
        else:
            # Save the latest page we know works in the chain
            db.update_page(next)

        for c in top_dict['data']['children']:
            db.write(c['data'])
        time.sleep(5)
        fetch_reddit_json(curr_depth, db, next, max_depth)
    return

def main():
    db = JSONDataBase('reddit_search')
    next = db.get_last_page()
    fetch_reddit_json(0, db, next)
    db.close()
    return

if __name__ == '__main__':
    main()