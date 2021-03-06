# Basset Hound

Basset Hound objective is to hunt down possible secret candidates by feeding me static data and I'll try to sniff out
hidden secrets according to my heuristics.

In the end you'll get an output with the source information where I found this candidate and why I think
it's sensitive information that should not be there.

## Build & Run

### Docker

You can use this projects from https://hub.docker.com/r/filipecabaco/basset-hound/

#### Run 

`docker run -v <folder>:/tmp/<folder> basset`

You can add as many volumes as you want, just be sure the /tmp/ destination is different for each one.

If you want to override settings you can run the following command:
`docker run -v <Project Path>:/tmp/project -v <Output Path>:/tmp basset java -jar basset-hound.jar -f /tmp/ -o json -t /tmp/result`

This will run basset and save the result on the output folder with the filename out. To see more about the arguments, please check the `Arguments` section below.


### SBT
#### Run

`java -jar target/scala-2.11/bassethound-assembly-0.1.jar -f <folders>` on JAR

#### Build  

`sbt assembly`

Note: The assembly sbt plugin is included on the project.

## Command details

`java -jar <assembled jar> --help -c <Config Path> -f <Files> -e <Exluded Files>  -p <Output Present> -o <Output Format> -t <Output Path> -a <Aggregation Type>`

###Arguments
#### Help

 Check all the commands with `--help

#### Files

Defined using `-f` or `--files` followed by comma separated absolute paths for directories or files

List of files or directories to be analysed by Basset Hound

#### Excluded Files

Defined using `-e` or `--excluded` followed by comma separated absolute paths for directories or files

List of files or directories that will not be analysed

#### Output Present

Defined using `-d` or `--display` followed by the following options

Defines how the output will be presented

* Console - Prints the output on the console
* File - Saves the output onto a file
* Report - Saves a HTML report
    * Note: This will force the output to be of type "JSON" to be used by the report

#### Output Type

Defined using `-o` or `--output` followed by the following options:

* Pretty Print - `-t pretty` - Used for console output

    ```
    Total occurrences: 1
    Source: /some/folder/file
    Occurences: 1
      	Heuristic: NumericHeuristic
     		Candidate: 1234ABCD
     		Line: 1
     		Score: 0.5
   ```

* JSON - `-t json` - Compact JSON

    `{"_1":1 "_2":{"/some/folder/file":{"_1":1,"_2":{"NumericHeuristic":[{"_1":"1234ABCD","_2":0,"_3":0.50}]}}}`

* Pretty JSON - `-t pretty-json` - Pretty JSON

   ```
   "_1":1
   "_2":{
        "/some/folder/file":{
            "_1":1,
            "_2":{
            "NumericHeuristic":[
                {
                    "_1":"1234ABCD",
                    "_2":0,
                    "_3":0.5
                }
            ]
            }
        }
    }

   ```

The default behaviour it's to use `Pretty Print`

#### Output Target File

Defined using `-t` or `--target` followed by the absolute path of a directory or directory/filename

Target location to save output from analysis

* If file exists, throws exception
* If file does not exist, creates file with specified name
* If target specified is a folder, creates file `out`


#### Aggregate Type

Defined using `-a` or `--aggregate` followed by the following options:

* Source - `-a source` - Aggregate that will return Map(Source, Map(Heuristic, Result))
* Heuristic - `-a heuristic` - Aggregate that will return Map(Heuristic, Map(Source, Result))
* Score - `-a score` - Aggregate that will return Map(Source, Map(Line, Result))

Note: When you aggregate on score, the score is summed by line and the candidate is the full line. On pretty print we remove the line


#### Configuration File

Defined using `-c` or `--conf` followed by the absolute path of the config file

example.conf:

```
    
    files=[<Files / Directories to check>]

    excluded=[<Files / Directories to exclude>]

    output=<Output target>

    type=<Output Type>
    
```

## Components

The main components of Basset Hound are:

* Readers - This are in charge of the inputs (files/ http/ raw text)
* Feeders - After the readers provide the data, this element separates and prepares the data to be analysed, so at this
point you should be aware of how each heuristic works and what kind of input they prefer (e.g. by word, by line, full text)
* Heuristics - At this point we actually analyse the stream given from the feeders and attribute a score. This score is
specified by each heuristic. Since each heuristic specifies it's own score the filtering is also done here.
* Sniffer - This connects all elements and prepares everything to be returned correctly.

This way you can have a simple chain of Reader --> Feeder --> Heuristic that will provide us the framework for static content analysis.

## Currently implemented

### Readers

* FileReader - Given a java.io.File, transforms each line into a Stream[String]
* RawTextReader - Given a String, returns that String has a Stream[String]

### Feeders

* WordFeeder - Given a Stream[String] returns a Stream[String] with all the words received
* LineFeeder - Given a Stream[String] unites all of them and then returns a Stream[String] separated by break-lines

### Heuristics

* NumericHeuristic - Given a Stream[String] calculates the percentage of numeric characters and returns elements with more than 40% numeric characters.
    * If only numbers, returns -1 because this are not valid candidates for us
* KeywordHeuristic - Given a Stream[String] tries to match a regex with the given String
    * Regex example: `(keyword1|keyword2)\s+(separator1|separator2)\s+(\S+)`
    * With this regex we can extract the following groups:
        * Group 1 - Keyword found
        * Group 2 - Separator found
        * Group 3 - Candidate found

### Sniffers

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
