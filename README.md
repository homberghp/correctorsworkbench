correctorsworkbench
===================

netbeans plugin to prepare and correct performance assessments in an electronic exam setting

In modern education, your exam setting is as natural and realistic to the student as posible.
If they learn to program/write SQL querys/etc during the course using a|their computer, then the exam
should provide that same environment.

The aspect of fraude, potentially possible when using the personally owned and managed laptop in an exam setting
is a separate issue, that is not addressed here. We have solved that by providing the students with a live system
on a usb thumb-drive, which we prepare and configure.

Correctors workbench
--------------------
The setting which correctors workbench addresses is one of a
electronic exam, in which the candidates task is to write program
source code in some code writers tool (IDE). The assumption is that
the examiner prepares the exam from some set of programming tasks
(e.g. add code to method bodies, write sql queries etc), in which the
actual work  for the candidate is to fill the 'blank parts'. The exam
consists of one ore more source files (with the blank parts) and a
description  of the task. The description can be given on paper or be
intermixed with the source code.

An example in Java would be: the task is
described in the (Java-doc) method header. These in those parts of the
solution that have been removed beforehand by the examiner.

The students hand in their solution by somehow uploading their work to
a server. In our case (Fontys Venlo software engineering business
informatics) we use a version control system as the mechanism to hand
in work. A the moment of writing this is subversion.

A correctors workbench has the following requirements:

* Help prepare the exam: prepare exam from solution or existing program or script.
* Collect the exam from the server
* enable the corrector to work through the set of exams in task, student order
* view student answer to task and example solution on the same screen (in two separate ereas)
* be able to add a grade per task in any grading system.
* view progress per task-set and for the whole exame
* split the workload between examiners, each correcting different tasks.
* add remarks to the tasks (motivation for a given grade)
* collect the individual end grades per students in some tabular format


It is assumed that the collecting of the grades per task is the final step. Any weighting, overall grade correcting
or otherwise processing the grades into a format that is acceptable to the local administration is left to the user.
We us a database (to provide multiple examiners to work in parallel) and export the grades from a view to a spreadsheet, which is
ready for handing in.

A web version, which is used now, gives some ideas on the elements in the windows, is shown below.


![](https://raw.github.com/homberghp/correctorsworkbench/master/doc/cwb-anonymised-775.png)