#! /bin/sh

cd `dirname $0`

ctags -a -R ./ ../roborescue-roborescue/modules/ 2>/dev/null

