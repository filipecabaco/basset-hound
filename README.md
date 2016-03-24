# Basset Hound

Basset Hound objective is to hunt down possible secret candidates by feeding me the data and I'll try to sniff out
hidden secrets according to my heuristics.

In the end you'll get an output with the file where I found this candidate and why I think it's sensitive information
that shouldn't be in your source code.

## Components

The main components of Basset Hound are:

* Readers - This are in charge of the inputs (files/ http/ raw text)
* Feeders - After the readers provide the data, this element separates and prepares the data to be analysed, so at this
point you should be aware of how each heuristic works and what kind of input they prefer (e.g. by word, by line, full text)
* Heuristics - At this point we actually analyse the stream given from the feeders and attribute a score. This score is
specified by each heuristic. Since each heuristic specifies it's own score the filtering is also done here.

This way you can have a simple chain of Reader ---> Feeder ---> Heuristic that will provide us the framework for content analysis.