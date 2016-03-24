# Basset Hound

Basset Hound objective is to hunt down possible secret candidates by feeding me static data and I'll try to sniff out
hidden secrets according to my heuristics.

In the end you'll get an output with the source information where I found this candidate and why I think
it's sensitive information that shouldn't be there.

## Components

The main components of Basset Hound are:

* Readers - This are in charge of the inputs (files/ http/ raw text)
* Feeders - After the readers provide the data, this element separates and prepares the data to be analysed, so at this
point you should be aware of how each heuristic works and what kind of input they prefer (e.g. by word, by line, full text)
* Heuristics - At this point we actually analyse the stream given from the feeders and attribute a score. This score is
specified by each heuristic. Since each heuristic specifies it's own score the filtering is also done here.
* Sniffer - This connects all elements and prepares everything to be returned correctly. 

This way you can have a simple chain of Reader --> Feeder --> Heuristic --> Sniffer that will provide us the framework for static content analysis.

## Future Goals

In the future we want to save and detect future suspect changes in your code to prevent new sensitive information in it.

The plan is to accomplish this by checking the origin file and your changed file and check if your changes added any
suspect candidate to it. If there's something that could represent a danger we will notify and ask you to check it.

This way, before merging your source code, you can have an analysis of your code for insecure elements and avoid it,
reducing the security risk of leaving secrets in the code.
