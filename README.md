# Basset Hound

Basset Hound objective is to hunt down possible secret candidates by feeding me static data and I'll try to sniff out
hidden secrets according to my heuristics.

In the end you'll get an output with the source information where I found this candidate and why I think
it's sensitive information that should not be there.

## Build & Run
### Build
#### SBT
* To build use  `sbt assembly`
* To run use `java -jar target/scala-2.11/bassethound-assembly-0.1.jar -f <folders>` on JAR

#### Docker
* To build use `docker build -t basset .`
* To run use `docker run -v <folder>:/tmp/<name> basset`
    * `<name>` should be unique for each volume

If you want to override settings you can run the following command:
`docker run -v <Project Path>:/tmp/project -v <Output Path>:/tmp basset java -jar basset-hound.jar -f /tmp/ -t json -o /tmp/result`

This will run basset and save the result on the output folder with the filename out.


## Command details

`java -jar <assembled jar> -f <Files> -e <Exluded Files> -o <Output Path> -t <Output Format>`

###Arguments
#### Files

Defined using `-f` or `--files` followed by comma separated paths for directories or files

List of files or directories to be analysed by Basset Hound

#### Excluded Files

Defined using `-e` or `--excluded` followed by comma separated paths for directories or files

List of files or directories that will not be analysed

#### Output File

Defined using `-o` or `--output` followed by the path of a directory or directory/filename

Target location to save output from analysis

* If file exists, throws exception
* If file does not exist, creates file with specified name
* If target specified is a folder, creates file `out`

#### Output Type

Defined using `-t` or `--type` followed by the following options:

* Pretty Print - `-t pretty` - Used for console output

    ```
    Source: /some/folder/file
      	Heuristic: NumericHeuristic
     		Candidate: 1234ABCD
     		Line: 1
     		Score: 0.5
   ```
     		
* JSON - `-t json` - Compact JSON

    `{"/some/folder/file":{"NumericHeuristic":[{"_1":"1234ABCD","_2":0,"_3":0.5}]}}`

* Pretty JSON - `-t pretty-json` - Pretty JSON

   ```
    {
       "/some/folder/file":{
         "NumericHeuristic":[
           {
             "_1":"1234ABCD",
             "_2":0,
             "_3":0.5
           }
         ]
       }
     }
   ```

The default behaviour it's to use Pretty Print

## Components

The main components of Basset Hound are:

* Readers - This are in charge of the inputs (files/ http/ raw text)
* Feeders - After the readers provide the data, this element separates and prepares the data to be analysed, so at this
point you should be aware of how each heuristic works and what kind of input they prefer (e.g. by word, by line, full text)
* Heuristics - At this point we actually analyse the stream given from the feeders and attribute a score. This score is
specified by each heuristic. Since each heuristic specifies it's own score the filtering is also done here.
* Sniffer - This connects all elements and prepares everything to be returned correctly.

This way you can have a simple chain of Reader --> Feeder --> Heuristic --> Sniffer that will provide us the framework for static content analysis.

## Currently implemented

###Readers

* FileReader - Given a java.io.File, transforms each line into a Stream[String]
* RawTextReader - Given a String, returns that String has a Stream[String]

###Feeders

* WordFeeder - Given a Stream[String] returns a Stream[String] with all the words received
* LineFeeder - Given a Stream[String] unites all of them and then returns a Stream[String] separated by break-lines

###Heuristics

* NumericHeuristic - Given a Stream[String] calculates the percentage of numeric characters and returns elements with more than 40% numeric characters.
    * If only numbers, returns -1 because this are not valid candidates for us
* KeywordHeuristic - Given a Stream[String] tries to match a regex with the given String
    * Regex example: `(keyword1|keyword2)\s+(separator1|separator2)\s+(\S+)`
    * With this regex we can extract the following groups:
        * Group 1 - Keyword found
        * Group 2 - Separator found
        * Group 3 - Candidate found

###Sniffers

* NumericFileSniffer - Implements the following flow:
    * ```FileReader --> WordFeeder --> NumericHeuristic```
* NumericStringSniffer - Implements the following flow:
    * ```RawTextReader --> WordFeeder --> NumericHeuristic```
* KeywordFileSniffer - Implements the following flow:
    * ```FileReader --> LineFeeder --> KeywordHeuristic```
* KeywordStringSniffer - Implements the following flow:
    * ```RawTextReader --> LineFeeder --> KeywordHeuristic```

## Future Goals

In the future we want to save and detect future suspect changes in your code to prevent new sensitive information in it.

The plan is to accomplish this by checking the origin file and your changed file and check if your changes added any
suspect candidate to it. If there's something that could represent a danger we will notify and ask you to check it.

This way, before merging your source code, you can have an analysis of your code for insecure elements and avoid it,
reducing the security risk of leaving secrets in the code.
