begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003 Morten O. Alver and Nizar N. Batada  *  * All programs in this directory and subdirectories are published under the GNU  * General Public License as described below.  *  * This program is free software; you can redistribute it and/or modify it under  * the terms of the GNU General Public License as published by the Free Software  * Foundation; either version 2 of the License, or (at your option) any later  * version.  *  * This program is distributed in the hope that it will be useful, but WITHOUT  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS  * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more  * details.  *  * You should have received a copy of the GNU General Public License along with  * this program; if not, write to the Free Software Foundation, Inc., 59 Temple  * Place, Suite 330, Boston, MA 02111-1307 USA  *  * Further information about the GNU GPL is available at:  * http://www.gnu.org/copyleft/gpl.ja.html  *  */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

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
name|FileInputStream
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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|SortedSet
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexDatabase
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
name|BibtexEntry
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|KeyCollisionException
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
name|Util
import|;
end_import

begin_class
DECL|class|ImportFormatReader
specifier|public
class|class
name|ImportFormatReader
block|{
DECL|field|BIBTEX_FORMAT
specifier|public
specifier|static
name|String
name|BIBTEX_FORMAT
init|=
literal|"BibTeX"
decl_stmt|;
comment|/** all import formats, in the defalt order of import formats */
DECL|field|formats
specifier|private
name|SortedSet
name|formats
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
DECL|method|ImportFormatReader ()
specifier|public
name|ImportFormatReader
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|resetImportFormats ()
specifier|public
name|void
name|resetImportFormats
parameter_list|()
block|{
name|formats
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// Add all our importers to the TreeMap. The map is used to build the import
comment|// menus, and .
name|formats
operator|.
name|add
argument_list|(
operator|new
name|CsaImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|IsiImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|EndnoteImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|MedlineImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|BibteXMLImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|BiblioscapeImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|SixpackImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|InspecImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|ScifinderImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|OvidImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|RisImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|JstorImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|SilverPlatterImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|BiomailImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|RepecNepImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|PdfXmpImporter
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
operator|new
name|CopacImporter
argument_list|()
argument_list|)
expr_stmt|;
comment|// add all custom importers
for|for
control|(
name|Iterator
name|i
init|=
name|Globals
operator|.
name|prefs
operator|.
name|customImports
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
name|CustomImportList
operator|.
name|Importer
name|importer
init|=
operator|(
name|CustomImportList
operator|.
name|Importer
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
try|try
block|{
name|ImportFormat
name|imFo
init|=
name|importer
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|formats
operator|.
name|add
argument_list|(
name|imFo
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Could not instantiate "
operator|+
name|importer
operator|.
name|getName
argument_list|()
operator|+
literal|" importer, will ignore it. Please check if the class is still available."
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|/**    * Format for a given CLI-ID.    *     *<p>Will return the first format according to the default-order of    * format that matches the given ID.</p>    *     * @param cliId  CLI-Id    * @return  Import Format or<code>null</code> if none matches    */
DECL|method|getByCliId (String cliId)
specifier|public
name|ImportFormat
name|getByCliId
parameter_list|(
name|String
name|cliId
parameter_list|)
block|{
name|ImportFormat
name|result
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|formats
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
operator|&&
name|result
operator|==
literal|null
condition|;
control|)
block|{
name|ImportFormat
name|format
init|=
operator|(
name|ImportFormat
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|format
operator|.
name|getCLIId
argument_list|()
operator|.
name|equals
argument_list|(
name|cliId
argument_list|)
condition|)
block|{
name|result
operator|=
name|format
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|method|importFromStream (String format, InputStream in)
specifier|public
name|List
name|importFromStream
parameter_list|(
name|String
name|format
parameter_list|,
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|ImportFormat
name|importer
init|=
name|getByCliId
argument_list|(
name|format
argument_list|)
decl_stmt|;
if|if
condition|(
name|importer
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unknown import format: "
operator|+
name|format
argument_list|)
throw|;
name|List
name|res
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|in
argument_list|)
decl_stmt|;
comment|// Remove all empty entries
if|if
condition|(
name|res
operator|!=
literal|null
condition|)
name|purgeEmptyEntries
argument_list|(
name|res
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
DECL|method|importFromFile (String format, String filename)
specifier|public
name|List
name|importFromFile
parameter_list|(
name|String
name|format
parameter_list|,
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
name|ImportFormat
name|importer
init|=
name|getByCliId
argument_list|(
name|format
argument_list|)
decl_stmt|;
if|if
condition|(
name|importer
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unknown import format: "
operator|+
name|format
argument_list|)
throw|;
return|return
name|importFromFile
argument_list|(
name|importer
argument_list|,
name|filename
argument_list|)
return|;
block|}
DECL|method|importFromFile (ImportFormat importer, String filename)
specifier|public
name|List
name|importFromFile
parameter_list|(
name|ImportFormat
name|importer
parameter_list|,
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
name|List
name|result
init|=
literal|null
decl_stmt|;
name|InputStream
name|stream
init|=
literal|null
decl_stmt|;
try|try
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|filename
argument_list|)
decl_stmt|;
name|stream
operator|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|stream
argument_list|)
condition|)
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Wrong file format"
argument_list|)
argument_list|)
throw|;
name|stream
operator|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|result
operator|=
name|importer
operator|.
name|importEntries
argument_list|(
name|stream
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|stream
operator|!=
literal|null
condition|)
name|stream
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
throw|throw
name|ex
throw|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|method|createDatabase (List bibentries)
specifier|public
specifier|static
name|BibtexDatabase
name|createDatabase
parameter_list|(
name|List
name|bibentries
parameter_list|)
block|{
name|purgeEmptyEntries
argument_list|(
name|bibentries
argument_list|)
expr_stmt|;
name|BibtexDatabase
name|database
init|=
operator|new
name|BibtexDatabase
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|bibentries
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
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
try|try
block|{
name|entry
operator|.
name|setId
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
comment|//ignore
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"KeyCollisionException [ addBibEntries(...) ]"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|database
return|;
block|}
comment|/**    * All custom importers.    *     *<p>Elements are in default order.</p>    *     * @return all custom importers, elements are of type {@link InputFormat}    */
DECL|method|getCustomImportFormats ()
specifier|public
name|SortedSet
name|getCustomImportFormats
parameter_list|()
block|{
name|SortedSet
name|result
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|this
operator|.
name|formats
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
name|ImportFormat
name|format
init|=
operator|(
name|ImportFormat
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|format
operator|.
name|getIsCustomImporter
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|format
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
comment|/**    * All built-in importers.    *     *<p>Elements are in default order.</p>    *     * @return all custom importers, elements are of type {@link InputFormat}    */
DECL|method|getBuiltInInputFormats ()
specifier|public
name|SortedSet
name|getBuiltInInputFormats
parameter_list|()
block|{
name|SortedSet
name|result
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|this
operator|.
name|formats
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
name|ImportFormat
name|format
init|=
operator|(
name|ImportFormat
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|format
operator|.
name|getIsCustomImporter
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|format
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
comment|/**    * All importers.    *     *<p>Elements are in default order.</p>    *     * @return all custom importers, elements are of type {@link InputFormat}    */
DECL|method|getImportFormats ()
specifier|public
name|SortedSet
name|getImportFormats
parameter_list|()
block|{
return|return
name|this
operator|.
name|formats
return|;
block|}
comment|/**    * Human readable list of all known import formats (name and CLI Id).    *     *<p>List is in default-order.</p>    *     * @return  human readable list of all known import formats    */
DECL|method|getImportFormatList ()
specifier|public
name|String
name|getImportFormatList
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|this
operator|.
name|formats
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
name|ImportFormat
name|imFo
init|=
operator|(
name|ImportFormat
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|int
name|pad
init|=
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
literal|14
operator|-
name|imFo
operator|.
name|getFormatName
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"  "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|imFo
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|pad
condition|;
name|j
operator|++
control|)
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" : "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|imFo
operator|.
name|getCLIId
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
name|String
name|res
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
return|return
name|res
return|;
comment|//.substring(0, res.length()-1);
block|}
comment|/**      * Expand initials, e.g. EH Wissler -> E. H. Wissler or Wissler, EH -> Wissler, E. H.      * @param name      * @return The name after expanding initials.      */
DECL|method|expandAuthorInitials (String name)
specifier|public
specifier|static
name|String
name|expandAuthorInitials
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|String
index|[]
name|authors
init|=
name|name
operator|.
name|split
argument_list|(
literal|" and "
argument_list|)
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
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
name|authors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|authors
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|", "
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|String
index|[]
name|names
init|=
name|authors
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|", "
argument_list|)
decl_stmt|;
if|if
condition|(
name|names
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|names
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|names
operator|.
name|length
operator|>
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|j
init|=
literal|1
init|;
name|j
operator|<
name|names
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|expandAll
argument_list|(
name|names
index|[
name|j
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|String
index|[]
name|names
init|=
name|authors
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
if|if
condition|(
name|names
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|expandAll
argument_list|(
name|names
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|j
init|=
literal|1
init|;
name|j
operator|<
name|names
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|names
index|[
name|j
index|]
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|i
operator|<
name|authors
operator|.
name|length
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
return|;
block|}
comment|//------------------------------------------------------------------------------
DECL|method|expandAll (String s)
specifier|public
specifier|static
name|String
name|expandAll
parameter_list|(
name|String
name|s
parameter_list|)
block|{
comment|//System.out.println("'"+s+"'");
comment|// Avoid arrayindexoutof.... :
if|if
condition|(
name|s
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
name|s
return|;
comment|// If only one character (uppercase letter), add a dot and return immediately:
if|if
condition|(
operator|(
name|s
operator|.
name|length
argument_list|()
operator|==
literal|1
operator|)
operator|&&
operator|(
name|Character
operator|.
name|isLetter
argument_list|(
name|s
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|&&
name|Character
operator|.
name|isUpperCase
argument_list|(
name|s
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|)
condition|)
return|return
name|s
operator|+
literal|"."
return|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|char
name|c
init|=
name|s
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
decl_stmt|,
name|d
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|s
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|d
operator|=
name|s
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|Character
operator|.
name|isLetter
argument_list|(
name|c
argument_list|)
operator|&&
name|Character
operator|.
name|isUpperCase
argument_list|(
name|c
argument_list|)
operator|&&
name|Character
operator|.
name|isLetter
argument_list|(
name|d
argument_list|)
operator|&&
name|Character
operator|.
name|isUpperCase
argument_list|(
name|d
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
name|c
operator|=
name|d
expr_stmt|;
block|}
if|if
condition|(
name|Character
operator|.
name|isLetter
argument_list|(
name|c
argument_list|)
operator|&&
name|Character
operator|.
name|isUpperCase
argument_list|(
name|c
argument_list|)
operator|&&
name|Character
operator|.
name|isLetter
argument_list|(
name|d
argument_list|)
operator|&&
name|Character
operator|.
name|isUpperCase
argument_list|(
name|d
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
return|;
block|}
DECL|method|checkAndCreateFile (String filename)
specifier|static
name|File
name|checkAndCreateFile
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|f
operator|.
name|exists
argument_list|()
operator|&&
operator|!
name|f
operator|.
name|canRead
argument_list|()
operator|&&
operator|!
name|f
operator|.
name|isFile
argument_list|()
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Error "
operator|+
name|filename
operator|+
literal|" is not a valid file and|or is not readable."
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|logger
argument_list|(
literal|"Error "
operator|+
name|filename
operator|+
literal|" is not a valid file and|or is not readable."
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
return|return
name|f
return|;
block|}
comment|//==================================================
comment|// Set a field, unless the string to set is empty.
comment|//==================================================
DECL|method|setIfNecessary (BibtexEntry be, String field, String content)
specifier|public
specifier|static
name|void
name|setIfNecessary
parameter_list|(
name|BibtexEntry
name|be
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|content
parameter_list|)
block|{
if|if
condition|(
operator|!
name|content
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|be
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
DECL|method|getReader (File f, String encoding)
specifier|public
specifier|static
name|Reader
name|getReader
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
name|InputStreamReader
name|reader
decl_stmt|;
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|f
argument_list|)
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
return|return
name|reader
return|;
block|}
DECL|method|getReaderDefaultEncoding (InputStream in)
specifier|public
specifier|static
name|Reader
name|getReaderDefaultEncoding
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStreamReader
name|reader
decl_stmt|;
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
name|in
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|reader
return|;
block|}
DECL|method|import_File (String format, String filename)
specifier|public
specifier|static
name|BibtexDatabase
name|import_File
parameter_list|(
name|String
name|format
parameter_list|,
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
name|BibtexDatabase
name|database
init|=
literal|null
decl_stmt|;
name|List
name|bibentries
init|=
literal|null
decl_stmt|;
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|f
operator|.
name|exists
argument_list|()
condition|)
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
operator|+
literal|": "
operator|+
name|filename
argument_list|)
throw|;
try|try
block|{
name|bibentries
operator|=
name|Globals
operator|.
name|importFormatReader
operator|.
name|importFromFile
argument_list|(
name|format
argument_list|,
name|filename
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
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
literal|"Could not resolve import format"
argument_list|)
operator|+
literal|" '"
operator|+
name|format
operator|+
literal|"'"
argument_list|)
throw|;
block|}
if|if
condition|(
name|bibentries
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import failed"
argument_list|)
argument_list|)
throw|;
comment|// Remove all empty entries:
name|purgeEmptyEntries
argument_list|(
name|bibentries
argument_list|)
expr_stmt|;
comment|// Add entries to database.
name|database
operator|=
operator|new
name|BibtexDatabase
argument_list|()
expr_stmt|;
name|Iterator
name|it
init|=
name|bibentries
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
try|try
block|{
name|entry
operator|.
name|setId
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
comment|//ignore
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"KeyCollisionException [ addBibEntries(...) ]"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|database
return|;
block|}
comment|/**    * Receives an ArrayList of BibtexEntry instances, iterates through them, and    * removes all entries that have no fields set. This is useful for rooting out    * an unsucessful import (wrong format) that returns a number of empty entries.    */
DECL|method|purgeEmptyEntries (List entries)
specifier|public
specifier|static
name|void
name|purgeEmptyEntries
parameter_list|(
name|List
name|entries
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|entries
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
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// Get all fields of the entry:
name|Object
index|[]
name|o
init|=
name|entry
operator|.
name|getAllFields
argument_list|()
decl_stmt|;
comment|// If there are no fields, remove the entry:
if|if
condition|(
name|o
operator|.
name|length
operator|==
literal|0
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**    * Tries to import a file by iterating through the available import filters,    * and keeping the import that seems most promising. Returns an Object array    * with two elements, 0: the name of the format used, 1: a List of entries.    */
DECL|method|importUnknownFormat (String filename)
specifier|public
name|Object
index|[]
name|importUnknownFormat
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|Object
name|entryList
init|=
literal|null
decl_stmt|;
name|String
name|usedFormat
init|=
literal|null
decl_stmt|;
name|int
name|bestResult
init|=
literal|0
decl_stmt|;
comment|// Cycle through all importers:
for|for
control|(
name|Iterator
name|i
init|=
name|getImportFormats
argument_list|()
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
name|ImportFormat
name|imFo
init|=
operator|(
name|ImportFormat
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
try|try
block|{
comment|// System.out.println("Trying format: "+imFo.getFormatName());
name|List
name|entries
init|=
name|importFromFile
argument_list|(
name|imFo
argument_list|,
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
name|entries
operator|!=
literal|null
condition|)
name|purgeEmptyEntries
argument_list|(
name|entries
argument_list|)
expr_stmt|;
name|int
name|entryCount
init|=
operator|(
operator|(
name|entries
operator|!=
literal|null
operator|)
condition|?
name|entries
operator|.
name|size
argument_list|()
else|:
literal|0
operator|)
decl_stmt|;
comment|// System.out.println("Entries: "+entryCount);
comment|// BibtexDatabase base = importFile(formats[i], filename);
if|if
condition|(
name|entryCount
operator|>
name|bestResult
condition|)
block|{
name|bestResult
operator|=
name|entryCount
expr_stmt|;
name|usedFormat
operator|=
name|imFo
operator|.
name|getFormatName
argument_list|()
expr_stmt|;
name|entryList
operator|=
name|entries
expr_stmt|;
comment|// System.out.println("Looks good: "+imFo.getFormatName());
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{ 			}
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Used format: "
operator|+
name|usedFormat
argument_list|)
expr_stmt|;
comment|// Finally, if all else fails, see if it is a BibTeX file:
if|if
condition|(
name|entryList
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|ParserResult
name|pr
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
operator|new
name|File
argument_list|(
name|filename
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
operator|>
literal|0
operator|)
operator|||
operator|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getStringCount
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|entryList
operator|=
name|pr
expr_stmt|;
name|pr
operator|.
name|setFile
argument_list|(
operator|new
name|File
argument_list|(
name|filename
argument_list|)
argument_list|)
expr_stmt|;
name|usedFormat
operator|=
name|BIBTEX_FORMAT
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
comment|//ex.printStackTrace();
block|}
block|}
return|return
operator|new
name|Object
index|[]
block|{
name|usedFormat
block|,
name|entryList
block|}
return|;
block|}
block|}
end_class

end_unit

