Entity Recognition and Disambiguation Challenge
=============

This package contains code for the [Entity Recognition and Disambiguation Challenge](http://web-ngram.research.microsoft.com/erd2014). 
It took me some time to configure all the stuff and I think it could be useful for other teams participating in the challenge.  

The package contains two utilities: 

  * code to index the freebase <-> wikipedia file provided by the organizers, in order to convert the wikipedia labels to freebase id (and to filter out invalid entities);
  * code to start the REST service (for now, just the short track).
  

### Index the entity.tsv file
  
Once you have downloaded the [entity.tsv file](http://web-ngram.research.microsoft.com/erd2014/entity.tsv) provided in the [Datasets page](http://web-ngram.research.microsoft.com/erd2014/Datasets.aspx), you can index it running 

    ./script/index.sh entity.tsv mapdb

This command will create a folder `mapdb` containing an index with all the mappings. At runtime you can access 
the index creating a WikipediaToFreebase object:

    WikipediaToFreebase wikiToFreebase = new WikipediaToFreebase("mapdb"); // the folder name

And then use:

    wikiToFreebase.getLabel("Diego_Maradona");

in order to retrieve the freebase-id for the entity `Diego_Maradona`. Please observe that wikipedia labels are case sensitive and that I split 
out the common prefix `/wikipedia/en_title/`  to make things more compact. 

### Rest Service 

I set up a REST service for the short track as required in the challenge. I put both a POST and a GET service, the GET service is useful to test
if everything works. In order to use it you only have to patch the [Annotator](src/main/java/it/cnr/isti/hpc/erd/Annotator.java) object and 
make sure that you return your list of [Annotations](src/main/java/it/cnr/isti/hpc/erd/Annotation.java). When you are ready, you only have to 
run the command

    mvn jetty:run 

and the rest service will answer at the address:

    http://$(your-ip-address):8080/erd-challenge/rest/shortTrack
   
I hope this will help ;) 

Diego


