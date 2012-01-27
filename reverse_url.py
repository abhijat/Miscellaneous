#!/usr/bin/env python

import sys,urllib, re, string
from BeautifulSoup import BeautifulSoup
from BeautifulSoup import BeautifulStoneSoup as bss

imgurl = sys.argv[1]
print "Getting page title.."
soup = BeautifulSoup(urllib.urlopen(imgurl))

title = str(soup.title.string).strip()
raw_query = unicode(bss(title, convertEntities=bss.ALL_ENTITIES))

print "Found this:\n", raw_query

newurl = "http://www.reddit.com/search?q=" + raw_query

print "Searching on reddit.."
new_soup = BeautifulSoup(urllib.urlopen(newurl))

results = new_soup.findAll('a', {"class" : "comments"})
print "Found top result:\n", results[0]
go_here = results[0]['href']
print go_here
