#!/usr/bin/env bash
source ./scripts/config.sh

EXPECTED_ARGS=2	

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` erd-rest-url dexter-eval-output.tsv"
  exit $E_BADARGS
fi

#GOLDENTRUTH="/Users/diego/Dropbox/phd/projects/erd-utils/erd-golden-truth/golden-truth.json"
GOLDENTRUTH="/Users/diego/Dropbox/phd/projects/erd-utils/erd-golden-truth/golden00008.json"


echo "annotate erd documents in $2"
$JAVA $CLI.PerformErdAnnotationCLI -url $1 -input $GOLDENTRUTH -output /tmp/tmp

echo "converting in dexter-eval format"
awk -F"	" '{print $1"\t"$6"\t"$2"\t"$3"\t"$4"\t"$5"\t"$7}' /tmp/tmp > $2



echo "dexter eval file in $3"

echo "evaluation"

cp $2 /tmp/eval.tsv

cd ~/workspace/dexter-eval
./scripts/evaluate.sh /tmp/eval.tsv ${GOLDENTRUTH/json/tsv} Mwm erd-conf-measures.txt ${2/.tsv/-spotting.html}
mv ${2/.tsv/-spotting.html} /tmp/tmp.html

./scripts/evaluate.sh /tmp/eval.tsv ${GOLDENTRUTH/json/tsv} Mwa erd-conf-measures.txt ${2/.tsv/.disambiguation.html}
mv ${2/.tsv/.disambiguation.html} /tmp/tmp1.html
cd -  
mv /tmp/tmp.html ${2/.tsv/-spotting.html}
mv /tmp/tmp1.html ${2/.tsv/.disambiguation.html}


echo "evaluation in ${2/tsv/html}"

echo "done"
