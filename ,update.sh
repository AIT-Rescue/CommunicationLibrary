#! /bin/sh

cd `dirname $0`

ctags -a -R ./ ../roborescue-roborescue/modules/ 2>/dev/null
git pull
./gradlew build || exit
git commit -am "update Comlib" && git push
