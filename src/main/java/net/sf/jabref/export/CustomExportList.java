begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeMap
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
name|Globals
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|EventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|SortedList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|BasicEventList
import|;
end_import

begin_comment
comment|/**  * This class handles user defined custom export formats. They are initially  * read from Preferences, and kept alphabetically (sorted by name). Formats can  * be added or removed. When modified, the sort() method must be called to make  * sure the formats stay properly sorted. When the method store() is called,  * export formats are written to Preferences.  */
end_comment

begin_class
DECL|class|CustomExportList
specifier|public
class|class
name|CustomExportList
block|{
DECL|field|list
specifier|private
specifier|final
name|EventList
argument_list|<
name|String
index|[]
argument_list|>
name|list
decl_stmt|;
DECL|field|sorted
specifier|private
specifier|final
name|SortedList
argument_list|<
name|String
index|[]
argument_list|>
name|sorted
decl_stmt|;
DECL|field|formats
specifier|private
specifier|final
name|TreeMap
argument_list|<
name|String
argument_list|,
name|ExportFormat
argument_list|>
name|formats
init|=
operator|new
name|TreeMap
argument_list|<
name|String
argument_list|,
name|ExportFormat
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|array
specifier|private
name|Object
index|[]
name|array
decl_stmt|;
DECL|method|CustomExportList (Comparator<String[]> comp)
specifier|public
name|CustomExportList
parameter_list|(
name|Comparator
argument_list|<
name|String
index|[]
argument_list|>
name|comp
parameter_list|)
block|{
name|list
operator|=
operator|new
name|BasicEventList
argument_list|<
name|String
index|[]
argument_list|>
argument_list|()
expr_stmt|;
name|sorted
operator|=
operator|new
name|SortedList
argument_list|<
name|String
index|[]
argument_list|>
argument_list|(
name|list
argument_list|,
name|comp
argument_list|)
expr_stmt|;
block|}
DECL|method|getCustomExportFormats ()
specifier|public
name|TreeMap
argument_list|<
name|String
argument_list|,
name|ExportFormat
argument_list|>
name|getCustomExportFormats
parameter_list|()
block|{
name|formats
operator|.
name|clear
argument_list|()
expr_stmt|;
name|readPrefs
argument_list|()
expr_stmt|;
return|return
name|formats
return|;
block|}
DECL|method|size ()
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|list
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|getSortedList ()
specifier|public
name|EventList
argument_list|<
name|String
index|[]
argument_list|>
name|getSortedList
parameter_list|()
block|{
return|return
name|sorted
return|;
block|}
DECL|method|readPrefs ()
specifier|private
name|void
name|readPrefs
parameter_list|()
block|{
name|formats
operator|.
name|clear
argument_list|()
expr_stmt|;
name|list
operator|.
name|clear
argument_list|()
expr_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
name|String
index|[]
name|s
decl_stmt|;
while|while
condition|(
operator|(
name|s
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"customExportFormat"
operator|+
name|i
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|ExportFormat
name|format
init|=
name|createFormat
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
name|format
operator|!=
literal|null
condition|)
block|{
name|formats
operator|.
name|put
argument_list|(
name|format
operator|.
name|getConsoleName
argument_list|()
argument_list|,
name|format
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|customExportFormat
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"customExportFormat"
operator|+
name|i
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error initializing custom export format from string '%0'"
argument_list|,
name|customExportFormat
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
block|}
DECL|method|createFormat (String[] s)
specifier|private
name|ExportFormat
name|createFormat
parameter_list|(
name|String
index|[]
name|s
parameter_list|)
block|{
if|if
condition|(
name|s
operator|.
name|length
operator|<
literal|3
condition|)
block|{
return|return
literal|null
return|;
block|}
name|String
name|lfFileName
decl_stmt|;
if|if
condition|(
name|s
index|[
literal|1
index|]
operator|.
name|endsWith
argument_list|(
literal|".layout"
argument_list|)
condition|)
block|{
name|lfFileName
operator|=
name|s
index|[
literal|1
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|s
index|[
literal|1
index|]
operator|.
name|length
argument_list|()
operator|-
literal|7
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|lfFileName
operator|=
name|s
index|[
literal|1
index|]
expr_stmt|;
block|}
name|ExportFormat
name|format
init|=
operator|new
name|ExportFormat
argument_list|(
name|s
index|[
literal|0
index|]
argument_list|,
name|s
index|[
literal|0
index|]
argument_list|,
name|lfFileName
argument_list|,
literal|null
argument_list|,
name|s
index|[
literal|2
index|]
argument_list|)
decl_stmt|;
name|format
operator|.
name|setCustomExport
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|format
return|;
block|}
DECL|method|getElementAt (int pos)
specifier|public
name|String
index|[]
name|getElementAt
parameter_list|(
name|int
name|pos
parameter_list|)
block|{
return|return
operator|(
name|String
index|[]
operator|)
operator|(
name|array
index|[
name|pos
index|]
operator|)
return|;
block|}
DECL|method|addFormat (String[] s)
specifier|public
name|void
name|addFormat
parameter_list|(
name|String
index|[]
name|s
parameter_list|)
block|{
name|list
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|ExportFormat
name|format
init|=
name|createFormat
argument_list|(
name|s
argument_list|)
decl_stmt|;
name|formats
operator|.
name|put
argument_list|(
name|format
operator|.
name|getConsoleName
argument_list|()
argument_list|,
name|format
argument_list|)
expr_stmt|;
block|}
DECL|method|remove (String[] toRemove)
specifier|public
name|void
name|remove
parameter_list|(
name|String
index|[]
name|toRemove
parameter_list|)
block|{
name|ExportFormat
name|format
init|=
name|createFormat
argument_list|(
name|toRemove
argument_list|)
decl_stmt|;
name|formats
operator|.
name|remove
argument_list|(
name|format
operator|.
name|getConsoleName
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|remove
argument_list|(
name|toRemove
argument_list|)
expr_stmt|;
block|}
DECL|method|store ()
specifier|public
name|void
name|store
parameter_list|()
block|{
if|if
condition|(
name|list
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|purge
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
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
name|list
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
comment|// System.out.println(i+"..");
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
literal|"customExportFormat"
operator|+
name|i
argument_list|,
name|list
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|purge
argument_list|(
name|list
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|purge (int from)
specifier|private
name|void
name|purge
parameter_list|(
name|int
name|from
parameter_list|)
block|{
name|int
name|i
init|=
name|from
decl_stmt|;
while|while
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"customExportFormat"
operator|+
name|i
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|remove
argument_list|(
literal|"customExportFormat"
operator|+
name|i
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

