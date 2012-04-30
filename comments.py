#!/usr/bin/env python

import json
from pprint import pprint
import urllib, sys

def get_body(obj):
    print 'Processing %s' % obj.__str__
    if 'body' in obj.keys():
        print obj['body']
    else:
        for k in obj.keys():
            if type(k) == 'dict':
                get_body(k)

url = sys.argv[1]
txt = urllib.urlopen(url).read()
objects = json.loads(txt)
for o in objects:
    if type(o) == 'dict':
        get_body(o)
