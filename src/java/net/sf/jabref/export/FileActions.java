begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|export
operator|.
name|layout
operator|.
name|Layout
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
name|export
operator|.
name|layout
operator|.
name|LayoutHelper
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
name|*
import|;
end_import

begin_comment
comment|/**  * DOCUMENT ME!  *  * @author $author$  * @version $Revision$  */
end_comment

begin_class
DECL|class|FileActions
specifier|public
class|class
name|FileActions
block|{
comment|//~ Methods ////////////////////////////////////////////////////////////////
DECL|method|initFile (File file, boolean backup)
specifier|private
specifier|static
name|void
name|initFile
parameter_list|(
name|File
name|file
parameter_list|,
name|boolean
name|backup
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|name
init|=
name|file
operator|.
name|getName
argument_list|()
decl_stmt|;
name|String
name|path
init|=
name|file
operator|.
name|getParent
argument_list|()
decl_stmt|;
name|File
name|temp
init|=
operator|new
name|File
argument_list|(
name|path
argument_list|,
name|name
operator|+
name|GUIGlobals
operator|.
name|tempExt
argument_list|)
decl_stmt|;
if|if
condition|(
name|backup
condition|)
block|{
name|File
name|back
init|=
operator|new
name|File
argument_list|(
name|path
argument_list|,
name|name
operator|+
name|GUIGlobals
operator|.
name|backupExt
argument_list|)
decl_stmt|;
if|if
condition|(
name|back
operator|.
name|exists
argument_list|()
condition|)
block|{
name|back
operator|.
name|renameTo
argument_list|(
name|temp
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|file
operator|.
name|renameTo
argument_list|(
name|back
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|temp
operator|.
name|exists
argument_list|()
condition|)
block|{
name|temp
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|file
operator|.
name|renameTo
argument_list|(
name|temp
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|writePreamble (Writer fw, String preamble)
specifier|private
specifier|static
name|void
name|writePreamble
parameter_list|(
name|Writer
name|fw
parameter_list|,
name|String
name|preamble
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|preamble
operator|!=
literal|null
condition|)
block|{
name|fw
operator|.
name|write
argument_list|(
literal|"@PREAMBLE{"
argument_list|)
expr_stmt|;
name|fw
operator|.
name|write
argument_list|(
name|preamble
argument_list|)
expr_stmt|;
name|fw
operator|.
name|write
argument_list|(
literal|"}\n\n"
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|writeStrings (Writer fw, BibtexDatabase database)
specifier|private
specifier|static
name|void
name|writeStrings
parameter_list|(
name|Writer
name|fw
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
throws|throws
name|IOException
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|database
operator|.
name|getStringCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BibtexString
name|bs
init|=
name|database
operator|.
name|getString
argument_list|(
name|i
argument_list|)
decl_stmt|;
comment|//fw.write("@STRING{"+bs.getName()+" = \""+bs.getContent()+"\"}\n\n");
name|fw
operator|.
name|write
argument_list|(
literal|"@STRING{"
operator|+
name|bs
operator|.
name|getName
argument_list|()
operator|+
literal|" = "
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|bs
operator|.
name|getContent
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|fw
operator|.
name|write
argument_list|(
operator|(
operator|new
name|LatexFieldFormatter
argument_list|()
operator|)
operator|.
name|format
argument_list|(
name|bs
operator|.
name|getContent
argument_list|()
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|fw
operator|.
name|write
argument_list|(
literal|"{}"
argument_list|)
expr_stmt|;
comment|//Util.writeField(bs.getName(), bs.getContent(), fw) ;
name|fw
operator|.
name|write
argument_list|(
literal|"}\n\n"
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|repairAfterError (File file)
specifier|public
specifier|static
name|void
name|repairAfterError
parameter_list|(
name|File
name|file
parameter_list|)
block|{
comment|// Repair the file with our temp file since saving failed.
name|String
name|name
init|=
name|file
operator|.
name|getName
argument_list|()
decl_stmt|;
comment|// Repair the file with our temp file since saving failed.
name|String
name|path
init|=
name|file
operator|.
name|getParent
argument_list|()
decl_stmt|;
name|File
name|temp
init|=
operator|new
name|File
argument_list|(
name|path
argument_list|,
name|name
operator|+
name|GUIGlobals
operator|.
name|tempExt
argument_list|)
decl_stmt|;
name|File
name|back
init|=
operator|new
name|File
argument_list|(
name|path
argument_list|,
name|name
operator|+
name|GUIGlobals
operator|.
name|backupExt
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|file
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|temp
operator|.
name|exists
argument_list|()
condition|)
block|{
name|temp
operator|.
name|renameTo
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|back
operator|.
name|renameTo
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Saves the database to file. Two boolean values indicate whether      * only entries with a nonzero Globals.SEARCH value and only      * entries with a nonzero Globals.GROUPSEARCH value should be      * saved. This can be used to let the user save only the results of      * a search. False and false means all entries are saved.      */
DECL|method|saveDatabase (BibtexDatabase database, MetaData metaData, File file, JabRefPreferences prefs, boolean checkSearch, boolean checkGroup)
specifier|public
specifier|static
name|void
name|saveDatabase
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|File
name|file
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|,
name|boolean
name|checkSearch
parameter_list|,
name|boolean
name|checkGroup
parameter_list|)
throws|throws
name|SaveException
block|{
name|BibtexEntry
name|be
init|=
literal|null
decl_stmt|;
try|try
block|{
name|initFile
argument_list|(
name|file
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"backup"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Define our data stream.
comment|//Writer fw = getWriter(file, "UTF-8");
name|FileWriter
name|fw
init|=
operator|new
name|FileWriter
argument_list|(
name|file
argument_list|)
decl_stmt|;
comment|// Write signature.
name|fw
operator|.
name|write
argument_list|(
name|GUIGlobals
operator|.
name|SIGNATURE
argument_list|)
expr_stmt|;
comment|// Write preamble if there is one.
name|writePreamble
argument_list|(
name|fw
argument_list|,
name|database
operator|.
name|getPreamble
argument_list|()
argument_list|)
expr_stmt|;
comment|// Write strings if there are any.
name|writeStrings
argument_list|(
name|fw
argument_list|,
name|database
argument_list|)
expr_stmt|;
comment|// Write database entries. Take care, using CrossRefEntry-
comment|// Comparator, that referred entries occur after referring
comment|// ones. Apart from crossref requirements, entries will be
comment|// sorted as they appear on the screen.
name|String
name|pri
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"priSort"
argument_list|)
decl_stmt|;
name|String
name|sec
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"secSort"
argument_list|)
decl_stmt|;
comment|// sorted as they appear on the screen.
name|String
name|ter
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"terSort"
argument_list|)
decl_stmt|;
name|TreeSet
name|sorter
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|CrossRefEntryComparator
argument_list|(
operator|new
name|EntryComparator
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"priDescending"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"secDescending"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"terDescending"
argument_list|)
argument_list|,
name|pri
argument_list|,
name|sec
argument_list|,
name|ter
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|Set
name|keySet
init|=
name|database
operator|.
name|getKeySet
argument_list|()
decl_stmt|;
if|if
condition|(
name|keySet
operator|!=
literal|null
condition|)
block|{
name|Iterator
name|i
init|=
name|keySet
operator|.
name|iterator
argument_list|()
decl_stmt|;
for|for
control|(
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|sorter
operator|.
name|add
argument_list|(
name|database
operator|.
name|getEntryById
argument_list|(
call|(
name|String
call|)
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|FieldFormatter
name|ff
init|=
operator|new
name|LatexFieldFormatter
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|sorter
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|be
operator|=
call|(
name|BibtexEntry
call|)
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
comment|// Check if the entry should be written.
name|boolean
name|write
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|checkSearch
operator|&&
operator|!
name|nonZeroField
argument_list|(
name|be
argument_list|,
name|Globals
operator|.
name|SEARCH
argument_list|)
condition|)
block|{
name|write
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|checkGroup
operator|&&
operator|!
name|nonZeroField
argument_list|(
name|be
argument_list|,
name|Globals
operator|.
name|GROUPSEARCH
argument_list|)
condition|)
block|{
name|write
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|write
condition|)
block|{
name|be
operator|.
name|write
argument_list|(
name|fw
argument_list|,
name|ff
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|fw
operator|.
name|write
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Write meta data.
if|if
condition|(
name|metaData
operator|!=
literal|null
condition|)
block|{
name|metaData
operator|.
name|writeMetaData
argument_list|(
name|fw
argument_list|)
expr_stmt|;
block|}
name|fw
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|repairAfterError
argument_list|(
name|file
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|SaveException
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|be
argument_list|)
throw|;
block|}
block|}
comment|/**      * Saves the database to file, including only the entries included      * in the supplied input array bes.      */
DECL|method|savePartOfDatabase (BibtexDatabase database, MetaData metaData, File file, JabRefPreferences prefs, BibtexEntry[] bes)
specifier|public
specifier|static
name|void
name|savePartOfDatabase
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|File
name|file
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|,
name|BibtexEntry
index|[]
name|bes
parameter_list|)
throws|throws
name|SaveException
block|{
name|BibtexEntry
name|be
init|=
literal|null
decl_stmt|;
try|try
block|{
name|initFile
argument_list|(
name|file
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"backup"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Define our data stream.
name|FileWriter
name|fw
init|=
operator|new
name|FileWriter
argument_list|(
name|file
argument_list|)
decl_stmt|;
comment|// Write signature.
name|fw
operator|.
name|write
argument_list|(
name|GUIGlobals
operator|.
name|SIGNATURE
argument_list|)
expr_stmt|;
comment|// Write preamble if there is one.
name|writePreamble
argument_list|(
name|fw
argument_list|,
name|database
operator|.
name|getPreamble
argument_list|()
argument_list|)
expr_stmt|;
comment|// Write strings if there are any.
name|writeStrings
argument_list|(
name|fw
argument_list|,
name|database
argument_list|)
expr_stmt|;
comment|// Write database entries. Take care, using CrossRefEntry-
comment|// Comparator, that referred entries occur after referring
comment|// ones. Apart from crossref requirements, entries will be
comment|// sorted as they appear on the screen.
name|String
name|pri
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"priSort"
argument_list|)
decl_stmt|;
name|String
name|sec
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"secSort"
argument_list|)
decl_stmt|;
comment|// sorted as they appear on the screen.
name|String
name|ter
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"terSort"
argument_list|)
decl_stmt|;
name|TreeSet
name|sorter
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|CrossRefEntryComparator
argument_list|(
operator|new
name|EntryComparator
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"priDescending"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"secDescending"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"terDescending"
argument_list|)
argument_list|,
name|pri
argument_list|,
name|sec
argument_list|,
name|ter
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|bes
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|bes
operator|.
name|length
operator|>
literal|0
operator|)
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sorter
operator|.
name|add
argument_list|(
name|bes
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|FieldFormatter
name|ff
init|=
operator|new
name|LatexFieldFormatter
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|sorter
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|be
operator|=
call|(
name|BibtexEntry
call|)
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|be
operator|.
name|write
argument_list|(
name|fw
argument_list|,
name|ff
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|fw
operator|.
name|write
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
comment|// Write meta data.
if|if
condition|(
name|metaData
operator|!=
literal|null
condition|)
block|{
name|metaData
operator|.
name|writeMetaData
argument_list|(
name|fw
argument_list|)
expr_stmt|;
block|}
name|fw
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|repairAfterError
argument_list|(
name|file
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|SaveException
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|be
argument_list|)
throw|;
block|}
block|}
DECL|method|getWriter (File f, String encoding)
specifier|public
specifier|static
name|OutputStreamWriter
name|getWriter
parameter_list|(
name|File
name|f
parameter_list|,
name|String
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
name|OutputStreamWriter
name|ow
decl_stmt|;
comment|//try {
name|ow
operator|=
operator|new
name|OutputStreamWriter
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|f
argument_list|)
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
comment|//} catch (UnsupportedEncodingException ex) {
comment|//  ow = new OutputStreamWriter(new FileOutputStream(f));
comment|//}
return|return
name|ow
return|;
block|}
DECL|method|exportCustomDatabase (BibtexDatabase database, String directory, String lfName, File outFile, JabRefPreferences prefs)
specifier|public
specifier|static
name|void
name|exportCustomDatabase
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|String
name|directory
parameter_list|,
name|String
name|lfName
parameter_list|,
name|File
name|outFile
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|)
throws|throws
name|Exception
block|{
name|exportDatabase
argument_list|(
name|database
argument_list|,
name|directory
argument_list|,
name|lfName
argument_list|,
name|outFile
argument_list|,
name|prefs
argument_list|)
expr_stmt|;
block|}
DECL|method|exportDatabase (BibtexDatabase database, String lfName, File outFile, JabRefPreferences prefs)
specifier|public
specifier|static
name|void
name|exportDatabase
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|String
name|lfName
parameter_list|,
name|File
name|outFile
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|)
throws|throws
name|Exception
block|{
name|exportDatabase
argument_list|(
name|database
argument_list|,
name|Globals
operator|.
name|LAYOUT_PREFIX
argument_list|,
name|lfName
argument_list|,
name|outFile
argument_list|,
name|prefs
argument_list|)
expr_stmt|;
block|}
DECL|method|exportDatabase (BibtexDatabase database, String prefix, String lfName, File outFile, JabRefPreferences prefs)
specifier|public
specifier|static
name|void
name|exportDatabase
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|String
name|prefix
parameter_list|,
name|String
name|lfName
parameter_list|,
name|File
name|outFile
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|)
throws|throws
name|Exception
block|{
comment|//PrintStream ps=null;
name|OutputStreamWriter
name|ps
init|=
literal|null
decl_stmt|;
comment|//try {
name|Object
index|[]
name|keys
init|=
name|database
operator|.
name|getKeySet
argument_list|()
operator|.
name|toArray
argument_list|()
decl_stmt|;
name|String
name|key
decl_stmt|,
name|type
decl_stmt|;
name|Reader
name|reader
decl_stmt|;
name|int
name|c
decl_stmt|;
comment|// Trying to change the encoding:
comment|//ps=new PrintStream(new FileOutputStream(outFile));
name|ps
operator|=
operator|new
name|OutputStreamWriter
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|outFile
argument_list|)
argument_list|,
literal|"iso-8859-1"
argument_list|)
expr_stmt|;
comment|// Print header
try|try
block|{
name|reader
operator|=
name|getReader
argument_list|(
name|prefix
operator|+
name|lfName
operator|+
literal|".begin.layout"
argument_list|)
expr_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|ps
operator|.
name|write
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{}
comment|// If an exception was cast, export filter doesn't have a begin file.
comment|// Write database entrie; entries will be sorted as they
comment|// appear on the screen.
name|String
name|pri
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"priSort"
argument_list|)
decl_stmt|,
name|sec
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"secSort"
argument_list|)
decl_stmt|,
name|ter
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"terSort"
argument_list|)
decl_stmt|;
name|EntrySorter
name|sorter
init|=
name|database
operator|.
name|getSorter
argument_list|(
operator|new
name|EntryComparator
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"priDescending"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"secDescending"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"terDescending"
argument_list|)
argument_list|,
name|pri
argument_list|,
name|sec
argument_list|,
name|ter
argument_list|)
argument_list|)
decl_stmt|;
comment|// Load default layout
name|reader
operator|=
name|getReader
argument_list|(
name|prefix
operator|+
name|lfName
operator|+
literal|".layout"
argument_list|)
expr_stmt|;
comment|//Util.pr(prefix+lfName+".layout");
name|LayoutHelper
name|layoutHelper
init|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|Layout
name|defLayout
init|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|(
name|Globals
operator|.
name|FORMATTER_PACKAGE
argument_list|)
decl_stmt|;
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
name|HashMap
name|layouts
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|Layout
name|layout
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sorter
operator|.
name|getEntryCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
comment|// Get the entry
name|key
operator|=
operator|(
name|String
operator|)
name|sorter
operator|.
name|getIdAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|BibtexEntry
name|entry
init|=
name|database
operator|.
name|getEntryById
argument_list|(
name|key
argument_list|)
decl_stmt|;
comment|//System.out.println(entry.getType().getName());
comment|// Get the layout
name|type
operator|=
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
if|if
condition|(
name|layouts
operator|.
name|containsKey
argument_list|(
name|type
argument_list|)
condition|)
name|layout
operator|=
operator|(
name|Layout
operator|)
name|layouts
operator|.
name|get
argument_list|(
name|type
argument_list|)
expr_stmt|;
else|else
block|{
try|try
block|{
comment|// We try to get a type-specific layout for this entry.
name|reader
operator|=
name|getReader
argument_list|(
name|prefix
operator|+
name|lfName
operator|+
literal|"."
operator|+
name|type
operator|+
literal|".layout"
argument_list|)
expr_stmt|;
name|layoutHelper
operator|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|)
expr_stmt|;
name|layout
operator|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|(
name|Globals
operator|.
name|FORMATTER_PACKAGE
argument_list|)
expr_stmt|;
name|layouts
operator|.
name|put
argument_list|(
name|type
argument_list|,
name|layout
argument_list|)
expr_stmt|;
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|// The exception indicates that no type-specific layout exists, so we
comment|// go with the default one.
name|layout
operator|=
name|defLayout
expr_stmt|;
block|}
block|}
comment|//Layout layout = layoutHelper.getLayoutFromText();
comment|// Write the entry
name|ps
operator|.
name|write
argument_list|(
name|layout
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Print footer
try|try
block|{
name|reader
operator|=
name|getReader
argument_list|(
name|prefix
operator|+
name|lfName
operator|+
literal|".end.layout"
argument_list|)
expr_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|ps
operator|.
name|write
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{}
comment|// If an exception was cast, export filter doesn't have a end file.
name|ps
operator|.
name|flush
argument_list|()
expr_stmt|;
name|ps
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
comment|/**      * This method attempts to get a Reader for the file path given, either by      * loading it as a resource (from within jar), or as a normal file.      * If unsuccessful (e.g. file not found), an IOException is thrown.      */
DECL|method|getReader (String name)
specifier|private
specifier|static
name|Reader
name|getReader
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|IOException
block|{
name|Reader
name|reader
init|=
literal|null
decl_stmt|;
comment|// Try loading as a resource first. This works for files inside the jar:
name|URL
name|reso
init|=
name|JabRefFrame
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|name
argument_list|)
decl_stmt|;
comment|// If that didn't work, try loading as a normal file URL:
if|if
condition|(
name|reso
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
name|reso
operator|.
name|openStream
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not find layout file"
argument_list|)
operator|+
literal|": '"
operator|+
name|name
operator|+
literal|"'."
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|name
argument_list|)
decl_stmt|;
try|try
block|{
name|reader
operator|=
operator|new
name|FileReader
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not find layout file"
argument_list|)
operator|+
literal|": '"
operator|+
name|name
operator|+
literal|"'."
argument_list|)
throw|;
block|}
block|}
return|return
name|reader
return|;
block|}
comment|/** Returns true iff the entry has a nonzero value in its field.      */
DECL|method|nonZeroField (BibtexEntry be, String field)
specifier|private
specifier|static
name|boolean
name|nonZeroField
parameter_list|(
name|BibtexEntry
name|be
parameter_list|,
name|String
name|field
parameter_list|)
block|{
name|String
name|o
init|=
call|(
name|String
call|)
argument_list|(
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|(
operator|(
name|o
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|o
operator|.
name|equals
argument_list|(
literal|"0"
argument_list|)
operator|)
return|;
block|}
block|}
end_class

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  END OF FILE.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

end_unit

