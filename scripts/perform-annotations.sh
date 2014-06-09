#!/usr/bin/env bash
source ./scripts/config.sh

EXPECTED_ARGS=3

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` erd-rest-url erd-documents.json dexter-eval-output.tsv"
  exit $E_BADARGS
fi

echo "annotate erd documents in $2"
$JAVA $CLI.PerformErdAnnotationCLI -url $1 -input $2 -output /tmp/tmp

echo "converting in dexter-eval format"

awk -F"	" '{print $1"\t"$6"\t"$2"\t"$3"\t"$4"\t"$5"\t"$7}' /tmp/tmp > $3

echo "dexter eval file in $3"

echo "done"