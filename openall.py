#!/usr/bin/env python

bm_file = "/home/abhijat/.config/google-chrome/Default/Bookmarks"
bm_handle = open(bm_file, "r")

urls = ["google-chrome"]
for line in bm_handle:
    if "url\":" in line:
        url = line.split("\"")[3]
        urls.append(url)

if len(urls) > 1:
    import subprocess
    subprocess.call(urls)
bm_handle.close()
