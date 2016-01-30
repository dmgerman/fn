begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2004 R. Nagel Copyright (C) 2015-2016 T. Denkinger, JabRef Contributors  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_comment
comment|/**  *<p>Title: Latex Aux to Bibtex</p>  *<p>  *<p>Description: generates a sub-database which contains only bibtex entries  * from input aux file</p>  *<p>  *<p>Copyright: Copyright (c) 2004</p>  *<p>  *<p>Company:</p>  *  * @version 1.0  * @author r.nagel  * @todo Redesign of dialog structure for an assistant like feeling....  * Now - the unknown BibTeX entries cannot inserted into the reference  * database without closing the dialog.  */
end_comment

begin_comment
comment|// created by : r.nagel 23.08.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified : - 11.04.2005
end_comment

begin_comment
comment|//              handling \\@input{file.aux} tag in aux files (nested aux files)
end_comment

begin_package
DECL|package|net.sf.jabref.wizard.auximport
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|wizard
operator|.
name|auximport
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|IdGenerator
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabase
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexString
import|;
end_import

begin_class
DECL|class|AuxSubGenerator
specifier|public
class|class
name|AuxSubGenerator
block|{
DECL|field|mySet
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|mySet
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|(
literal|20
argument_list|)
decl_stmt|;
comment|// all unique bibtex keys in aux file
DECL|field|notFoundList
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|notFoundList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// all not solved bibtex keys
DECL|field|db
specifier|private
name|BibDatabase
name|db
decl_stmt|;
comment|// reference database
DECL|field|auxDB
specifier|private
name|BibDatabase
name|auxDB
decl_stmt|;
comment|// contains only the bibtex keys who found in aux file
DECL|field|nestedAuxCounter
specifier|private
name|int
name|nestedAuxCounter
decl_stmt|;
comment|// counts the nested aux files
DECL|field|crossreferencedEntriesCount
specifier|private
name|int
name|crossreferencedEntriesCount
decl_stmt|;
comment|// counts entries pulled in due to crossref
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|AuxSubGenerator
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|TAG_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|TAG_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\\\(citation|abx@aux@cite)\\{(.+)\\}"
argument_list|)
decl_stmt|;
DECL|method|AuxSubGenerator (BibDatabase refDBase)
specifier|public
name|AuxSubGenerator
parameter_list|(
name|BibDatabase
name|refDBase
parameter_list|)
block|{
name|db
operator|=
name|refDBase
expr_stmt|;
block|}
comment|/**      * parseAuxFile read the Aux file and fill up some intern data structures. Nested aux files (latex \\include)      * supported!      *      * @param filename String : Path to LatexAuxFile      * @return boolean, true = no error occurs      */
comment|// found at comp.text.tex
comment|//> Can anyone tell be the information held within a .aux file?  Is there a
comment|//> specific format to this file?
comment|//
comment|// I don't think there is a particular format. Every package, class
comment|// or document can write to the aux file. The aux file consists of LaTeX macros
comment|// and is read at the \begin{document} and again at the \end{document}.
comment|//
comment|// It usually contains information about existing labels
comment|//  \\newlabel{sec:Intro}{{1}{1}}
comment|// and citations
comment|//  \citation{hiri:conv:1993}
comment|// and macros to write information to other files (like toc, lof or lot files)
comment|//  \@writefile{toc}{\contentsline {section}{\numberline
comment|// {1}Intro}{1}}
comment|// but as I said, there can be a lot more
comment|// aux file :
comment|//
comment|// \\citation{x}  x = used reference of bibtex library entry
comment|//
comment|// \\@input{x}  x = nested aux file
comment|//
comment|// the \\bibdata{x} directive contains information about the
comment|// bibtex library file -> x = name of bib file
comment|//
comment|// \\bibcite{x}{y}
comment|//   x is a label for an item and y is the index in bibliography
DECL|method|parseAuxFile (String filename)
specifier|private
name|boolean
name|parseAuxFile
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
comment|// regular expressions
name|Matcher
name|matcher
decl_stmt|;
comment|// while condition
name|boolean
name|cont
decl_stmt|;
comment|// return value -> default: no error
name|boolean
name|back
init|=
literal|true
decl_stmt|;
comment|// file list, used for nested aux files
name|List
argument_list|<
name|String
argument_list|>
name|fileList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|5
argument_list|)
decl_stmt|;
name|fileList
operator|.
name|add
argument_list|(
name|filename
argument_list|)
expr_stmt|;
comment|// get the file path
name|File
name|dummy
init|=
operator|new
name|File
argument_list|(
name|filename
argument_list|)
decl_stmt|;
name|String
name|path
init|=
name|dummy
operator|.
name|getParent
argument_list|()
decl_stmt|;
if|if
condition|(
name|path
operator|==
literal|null
condition|)
block|{
name|path
operator|=
literal|""
expr_stmt|;
block|}
else|else
block|{
name|path
operator|=
name|path
operator|+
name|File
operator|.
name|separator
expr_stmt|;
block|}
name|nestedAuxCounter
operator|=
operator|-
literal|1
expr_stmt|;
comment|// count only the nested reads
comment|// index of current file in list
name|int
name|fileIndex
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|fileIndex
operator|<
name|fileList
operator|.
name|size
argument_list|()
condition|)
block|{
name|String
name|fName
init|=
name|fileList
operator|.
name|get
argument_list|(
name|fileIndex
argument_list|)
decl_stmt|;
try|try
init|(
name|BufferedReader
name|br
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|FileReader
argument_list|(
name|fName
argument_list|)
argument_list|)
init|)
block|{
name|cont
operator|=
literal|true
expr_stmt|;
while|while
condition|(
name|cont
condition|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|maybeLine
decl_stmt|;
try|try
block|{
name|maybeLine
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|br
operator|.
name|readLine
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ioe
parameter_list|)
block|{
name|maybeLine
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|maybeLine
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|String
name|line
init|=
name|maybeLine
operator|.
name|get
argument_list|()
decl_stmt|;
name|matcher
operator|=
name|TAG_PATTERN
operator|.
name|matcher
argument_list|(
name|line
argument_list|)
expr_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// extract the bibtex-key(s) XXX from \citation{XXX} string
name|int
name|len
init|=
name|matcher
operator|.
name|end
argument_list|()
operator|-
name|matcher
operator|.
name|start
argument_list|()
decl_stmt|;
if|if
condition|(
name|len
operator|>
literal|11
condition|)
block|{
name|String
name|str
init|=
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
decl_stmt|;
comment|// could be an comma separated list of keys
name|String
index|[]
name|keys
init|=
name|str
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
if|if
condition|(
name|keys
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|dummyStr
range|:
name|keys
control|)
block|{
if|if
condition|(
name|dummyStr
operator|!=
literal|null
condition|)
block|{
comment|// delete all unnecessary blanks and save key into an set
name|mySet
operator|.
name|add
argument_list|(
name|dummyStr
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
comment|// try to find a nested aux file
name|int
name|index
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|"\\@input{"
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|int
name|start
init|=
name|index
operator|+
literal|8
decl_stmt|;
name|int
name|end
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|'}'
argument_list|,
name|start
argument_list|)
decl_stmt|;
if|if
condition|(
name|end
operator|>
name|start
condition|)
block|{
name|String
name|str
init|=
name|path
operator|+
name|line
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|8
argument_list|,
name|end
argument_list|)
decl_stmt|;
comment|// if filename already in file list
if|if
condition|(
operator|!
name|fileList
operator|.
name|contains
argument_list|(
name|str
argument_list|)
condition|)
block|{
name|fileList
operator|.
name|add
argument_list|(
name|str
argument_list|)
expr_stmt|;
comment|// insert file into file list
block|}
block|}
block|}
block|}
else|else
block|{
name|cont
operator|=
literal|false
expr_stmt|;
block|}
block|}
name|nestedAuxCounter
operator|++
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot locate input file!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem opening file!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|fileIndex
operator|++
expr_stmt|;
comment|// load next file
block|}
return|return
name|back
return|;
block|}
comment|/**      * resolveTags Try to find an equivalent bibtex entry into reference database for all keys (found in aux file). This      * method will fill up some intern data structures.....      */
DECL|method|resolveTags ()
specifier|private
name|void
name|resolveTags
parameter_list|()
block|{
name|auxDB
operator|=
operator|new
name|BibDatabase
argument_list|()
expr_stmt|;
name|notFoundList
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// for all bibtex keys (found in aux-file) try to find an equivalent
comment|// entry into reference database
for|for
control|(
name|String
name|str
range|:
name|mySet
control|)
block|{
name|BibEntry
name|entry
init|=
name|db
operator|.
name|getEntryByKey
argument_list|(
name|str
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
name|notFoundList
operator|.
name|add
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|insertEntry
argument_list|(
name|auxDB
argument_list|,
name|entry
argument_list|)
expr_stmt|;
comment|// Check if the entry we just found references another entry which
comment|// we don't already have in our list of entries to include. If so,
comment|// pull in that entry as well:
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"crossref"
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|crossref
lambda|->
block|{
if|if
condition|(
operator|!
name|mySet
operator|.
name|contains
argument_list|(
name|crossref
argument_list|)
condition|)
block|{
name|BibEntry
name|refEntry
init|=
name|db
operator|.
name|getEntryByKey
argument_list|(
name|crossref
argument_list|)
decl_stmt|;
comment|/**                          * [ 1717849 ] Patch for aux import by Kai Eckert                          */
if|if
condition|(
name|refEntry
operator|==
literal|null
condition|)
block|{
name|notFoundList
operator|.
name|add
argument_list|(
name|crossref
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|insertEntry
argument_list|(
name|auxDB
argument_list|,
name|refEntry
argument_list|)
expr_stmt|;
name|crossreferencedEntriesCount
operator|++
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
comment|// If we have inserted any entries, make sure to copy the source database's preamble and
comment|// strings:
if|if
condition|(
name|auxDB
operator|.
name|getEntryCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|auxDB
operator|.
name|setPreamble
argument_list|(
name|db
operator|.
name|getPreamble
argument_list|()
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|keys
init|=
name|db
operator|.
name|getStringKeySet
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|keys
control|)
block|{
name|BibtexString
name|string
init|=
name|db
operator|.
name|getString
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|auxDB
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Insert a clone of the given entry. The clone is given a new unique ID.      *      * @param bibDB The database to insert into.      * @param entry The entry to insert a copy of.      */
DECL|method|insertEntry (BibDatabase bibDB, BibEntry entry)
specifier|private
name|void
name|insertEntry
parameter_list|(
name|BibDatabase
name|bibDB
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
name|BibEntry
name|clonedEntry
init|=
operator|(
name|BibEntry
operator|)
name|entry
operator|.
name|clone
argument_list|()
decl_stmt|;
name|clonedEntry
operator|.
name|setId
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|bibDB
operator|.
name|insertEntry
argument_list|(
name|clonedEntry
argument_list|)
expr_stmt|;
block|}
comment|/**      * generate Shortcut method for easy generation.      *      * @param auxFileName String      * @param bibDB BibDatabase - reference database      * @return Vector - contains all not resolved bibtex entries      */
DECL|method|generate (String auxFileName, BibDatabase bibDB)
specifier|public
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|generate
parameter_list|(
name|String
name|auxFileName
parameter_list|,
name|BibDatabase
name|bibDB
parameter_list|)
block|{
name|db
operator|=
name|bibDB
expr_stmt|;
name|parseAuxFile
argument_list|(
name|auxFileName
argument_list|)
expr_stmt|;
name|resolveTags
argument_list|()
expr_stmt|;
return|return
name|notFoundList
return|;
block|}
DECL|method|getGeneratedDatabase ()
specifier|public
name|BibDatabase
name|getGeneratedDatabase
parameter_list|()
block|{
if|if
condition|(
name|auxDB
operator|==
literal|null
condition|)
block|{
name|auxDB
operator|=
operator|new
name|BibDatabase
argument_list|()
expr_stmt|;
block|}
return|return
name|auxDB
return|;
block|}
DECL|method|emptyGeneratedDatabase ()
specifier|public
name|boolean
name|emptyGeneratedDatabase
parameter_list|()
block|{
if|if
condition|(
name|auxDB
operator|==
literal|null
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
name|auxDB
operator|.
name|getEntryCount
argument_list|()
operator|==
literal|0
return|;
block|}
DECL|method|getFoundKeysInAux ()
specifier|public
specifier|final
name|int
name|getFoundKeysInAux
parameter_list|()
block|{
return|return
name|mySet
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|getResolvedKeysCount ()
specifier|public
specifier|final
name|int
name|getResolvedKeysCount
parameter_list|()
block|{
return|return
name|auxDB
operator|.
name|getEntryCount
argument_list|()
operator|-
name|crossreferencedEntriesCount
return|;
block|}
DECL|method|getNotResolvedKeysCount ()
specifier|public
specifier|final
name|int
name|getNotResolvedKeysCount
parameter_list|()
block|{
return|return
name|notFoundList
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * Query the number of extra entries pulled in due to crossrefs from other entries.      *      * @return The number of additional entries pulled in due to crossref      */
DECL|method|getCrossreferencedEntriesCount ()
specifier|public
specifier|final
name|int
name|getCrossreferencedEntriesCount
parameter_list|()
block|{
return|return
name|crossreferencedEntriesCount
return|;
block|}
comment|/** reset all used data structures */
DECL|method|clear ()
specifier|public
specifier|final
name|void
name|clear
parameter_list|()
block|{
name|mySet
operator|.
name|clear
argument_list|()
expr_stmt|;
name|notFoundList
operator|.
name|clear
argument_list|()
expr_stmt|;
name|crossreferencedEntriesCount
operator|=
literal|0
expr_stmt|;
comment|// db = null ;  ???
block|}
comment|/**      * returns the number of nested aux files, read by the last call of generate method      */
DECL|method|getNestedAuxCounter ()
specifier|public
name|int
name|getNestedAuxCounter
parameter_list|()
block|{
return|return
name|this
operator|.
name|nestedAuxCounter
return|;
block|}
DECL|method|getInformation (boolean includeMissingEntries)
specifier|public
name|String
name|getInformation
parameter_list|(
name|boolean
name|includeMissingEntries
parameter_list|)
block|{
name|StringBuffer
name|result
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
comment|// print statistics
name|result
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"keys_in_database"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|db
operator|.
name|getEntryCount
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"found_in_aux_file"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|getFoundKeysInAux
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"resolved"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|getResolvedKeysCount
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"not_found"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|getNotResolvedKeysCount
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"crossreferenced entries included"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|getCrossreferencedEntriesCount
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|includeMissingEntries
operator|&&
operator|(
name|getNotResolvedKeysCount
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
for|for
control|(
name|String
name|entry
range|:
name|notFoundList
control|)
block|{
name|result
operator|.
name|append
argument_list|(
name|entry
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|nestedAuxCounter
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"nested_aux_files"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|nestedAuxCounter
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

