#!/bin/bash

doc_fail="https://api.telegram.org/bot$token/sendDocument?chat_id=1773117711"
chlog="https://api.telegram.org/bot$token/sendMessage?chat_id=$chatid" # send changelog to the ci channel
doc="https://api.telegram.org/bot$token/sendDocument?chat_id=$chatid" # send apks to the ci channel

send_build() { curl -F document=@"$1" "$doc" -F "parse_mode=html" -F caption="$text"; }
build_failed() { curl -F document=@"$1" "$doc_fail" -F "parse_mode=html" -F caption="$text_failed"; }
send_chlog() { curl -F text="$chlog_text" "$chlog" -F "parse_mode=html"; }
send_dew() { curl -F text="$dewider_text" "$chlog" -F "parse_mode=html"; }

start=$(date +"%s")
#./gradlew assembleUniversalRelease 2>&1 | tee -a loguni.txt
./gradlew assembleArm64Release 2>&1 | tee -a loga64.txt
./gradlew assembleArm32Release 2>&1 | tee -a loga32.txt
end=$(date +"%s")
bt=$(($end - $start))
#apkuni=$(find app/build/outputs/apk -name '*universal.apk')
apka64=$(find app/build/outputs/apk -name '*arm64-v8a.apk')
apka32=$(find app/build/outputs/apk -name '*armeabi-v7a.apk')
# zip -q9 apk.zip $apk

text_failed="
<b>Build failed )(</b>
 
<b>$commit</b>
 
<b>Author:</b> <code>$commit_author</code>
<b>SHA:</b> <code>$commit_sha</code>
<b>Build Time:</b> <code>$(($bt / 60)):$(($bt % 60))</code>
"

textuni="
<b>MD5:</b> <code>$(md5sum $apkuni | cut -d' ' -f1)</code>
"

texta64="
<b>MD5:</b> <code>$(md5sum $apka64 | cut -d' ' -f1)</code>
"

texta32="
<b>MD5:</b> <code>$(md5sum $apka32 | cut -d' ' -f1)</code>
"

    chlog_text="
    <b>$commit</b>
    <b>Author:</b> <code>$commit_author</code>
    <b>SHA:</b> <code>$commit_sha</code>
    <b>Build Time:</b> <code>$(($bt / 60)):$(($bt % 60))</code>
    #folders #pr #beta
    "

dewider_text="~~~~ ~~~~"

if [[ -f $apka64 && -f $apka32 ]]; then
    send_dew
    #text="$textuni"
    #send_build "$apkuni"
    text="$texta64"
    send_build "$apka64"
    text="$texta32"
    send_build "$apka32"
    send_chlog
else
    #build_failed loguni.txt
    build_failed loga64.txt
    build_failed loga32.txt
    exit 1
fi
