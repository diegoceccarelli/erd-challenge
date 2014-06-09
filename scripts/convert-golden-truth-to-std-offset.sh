#!/usr/bin/env bash
source ./scripts/config.sh

EXPECTED_ARGS=3

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` golden.tsv golden.json converted-golden-tsv"
  exit $E_BADARGS
fi

echo "convert $1 in $3"
$JAVA $CLI.ConvertGroundTruthFromByteOffsetToCharOffsetCLI -input $1 -docs $2 -output $3
