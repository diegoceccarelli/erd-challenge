#!/usr/bin/env bash
source ./scripts/config.sh

EXPECTED_ARGS=2

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: `basename $0` entity.tsv db-folder"
  exit $E_BADARGS
fi

echo "index wikipedia-label -> freebase-id mappings in $1 in folder $2"
$JAVA $CLI.IndexWikipediaLabelToFreebaseIdCLI -input $1 -dbdir $2
echo "done, mappings in $2"