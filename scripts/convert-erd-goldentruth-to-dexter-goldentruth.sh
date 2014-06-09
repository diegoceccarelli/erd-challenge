#!/usr/bin/env bash
source ./scripts/config.sh

EXPECTED_ARGS=2

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` erd-golden-truth.tsv dexter-eval-golden-truth.tsv"
  exit $E_BADARGS
fi

echo "convert erd golden truth $1 to dexter-eval golden truth $2"
$JAVA $CLI.ErdAssessmentToDexterEvalAssessmentCLI -input $1 -output /tmp/tmp
sort -k1 /tmp/tmp > $2
echo "done, goldentruth in $2"