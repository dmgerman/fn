begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|BufferedReader
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
name|List
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
name|*
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
name|AuthorList
import|;
end_import

begin_comment
comment|/**  * Imports an Ovid file.  */
end_comment

begin_class
DECL|class|OvidImporter
specifier|public
class|class
name|OvidImporter
extends|extends
name|ImportFormat
block|{
DECL|field|ovid_src_pat
specifier|private
specifier|static
specifier|final
name|Pattern
name|ovid_src_pat
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Source ([ \\w&\\-,:]+)\\.[ ]+([0-9]+)\\(([\\w\\-]+)\\):([0-9]+\\-?[0-9]+?)\\,.*([0-9][0-9][0-9][0-9])"
argument_list|)
decl_stmt|;
DECL|field|ovid_src_pat_no_issue
specifier|private
specifier|static
specifier|final
name|Pattern
name|ovid_src_pat_no_issue
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Source ([ \\w&\\-,:]+)\\.[ ]+([0-9]+):([0-9]+\\-?[0-9]+?)\\,.*([0-9][0-9][0-9][0-9])"
argument_list|)
decl_stmt|;
DECL|field|ovid_src_pat_2
specifier|private
specifier|static
specifier|final
name|Pattern
name|ovid_src_pat_2
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"([ \\w&\\-,]+)\\. Vol ([0-9]+)\\(([\\w\\-]+)\\) ([A-Za-z]+) ([0-9][0-9][0-9][0-9]), ([0-9]+\\-?[0-9]+)"
argument_list|)
decl_stmt|;
DECL|field|incollection_pat
specifier|private
specifier|static
specifier|final
name|Pattern
name|incollection_pat
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(.+)\\(([0-9][0-9][0-9][0-9])\\)\\. ([ \\w&\\-,:]+)\\.[ ]+\\(pp. ([0-9]+\\-?[0-9]+?)\\).[A-Za-z0-9, ]+pp\\. "
operator|+
literal|"([\\w, ]+): ([\\w, ]+)"
argument_list|)
decl_stmt|;
DECL|field|book_pat
specifier|private
specifier|static
specifier|final
name|Pattern
name|book_pat
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\(([0-9][0-9][0-9][0-9])\\)\\. [A-Za-z, ]+([0-9]+) pp\\. ([\\w, ]+): ([\\w, ]+)"
argument_list|)
decl_stmt|;
comment|//   public static Pattern ovid_pat_inspec= Pattern.compile("Source ([
comment|// \\w&\\-]+)");
comment|/**      * Return the name of this import format.      */
annotation|@
name|Override
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"Ovid"
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.imports.ImportFormat#getCLIId()      */
annotation|@
name|Override
DECL|method|getCLIId ()
specifier|public
name|String
name|getCLIId
parameter_list|()
block|{
return|return
literal|"ovid"
return|;
block|}
DECL|field|ovidPattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|ovidPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<[0-9]+>"
argument_list|)
decl_stmt|;
comment|/**      * Check whether the source is in the correct format for this importer.      */
annotation|@
name|Override
DECL|method|isRecognizedFormat (InputStream stream)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|InputStream
name|stream
parameter_list|)
throws|throws
name|IOException
block|{
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
name|ImportFormatReader
operator|.
name|getReaderDefaultEncoding
argument_list|(
name|stream
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|str
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|str
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
operator|&&
name|i
operator|<
literal|50
condition|)
block|{
if|if
condition|(
name|OvidImporter
operator|.
name|ovidPattern
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|find
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
name|i
operator|++
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Parse the entries in the source, and return a List of BibtexEntry      * objects.      */
annotation|@
name|Override
DECL|method|importEntries (InputStream stream, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|importEntries
parameter_list|(
name|InputStream
name|stream
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
throws|throws
name|IOException
block|{
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
name|ImportFormatReader
operator|.
name|getReaderDefaultEncoding
argument_list|(
name|stream
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|line
operator|.
name|isEmpty
argument_list|()
operator|&&
name|line
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|!=
literal|' '
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"__NEWFIELD__"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|line
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|items
init|=
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"<[0-9]+>"
argument_list|)
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
name|items
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|h
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|String
index|[]
name|fields
init|=
name|items
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|"__NEWFIELD__"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
name|int
name|linebreak
init|=
name|field
operator|.
name|indexOf
argument_list|(
literal|'\n'
argument_list|)
decl_stmt|;
name|String
name|fieldName
init|=
name|field
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|linebreak
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|content
init|=
name|field
operator|.
name|substring
argument_list|(
name|linebreak
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|// Check if this is the author field (due to a minor special treatment for this field):
name|boolean
name|isAuthor
init|=
name|fieldName
operator|.
name|indexOf
argument_list|(
literal|"Author"
argument_list|)
operator|==
literal|0
operator|&&
operator|!
name|fieldName
operator|.
name|contains
argument_list|(
literal|"Author Keywords"
argument_list|)
operator|&&
operator|!
name|fieldName
operator|.
name|contains
argument_list|(
literal|"Author e-mail"
argument_list|)
decl_stmt|;
comment|// Remove unnecessary dots at the end of lines, unless this is the author field,
comment|// in which case a dot at the end could be significant:
if|if
condition|(
operator|!
name|isAuthor
operator|&&
name|content
operator|.
name|endsWith
argument_list|(
literal|"."
argument_list|)
condition|)
block|{
name|content
operator|=
name|content
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|content
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
comment|//fields[j] = fields[j].trim();
if|if
condition|(
name|isAuthor
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|indexOf
argument_list|(
literal|"Title"
argument_list|)
operator|==
literal|0
condition|)
block|{
name|content
operator|=
name|content
operator|.
name|replaceAll
argument_list|(
literal|"\\[.+\\]"
argument_list|,
literal|""
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|content
operator|.
name|endsWith
argument_list|(
literal|"."
argument_list|)
condition|)
block|{
name|content
operator|=
name|content
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|content
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|h
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|indexOf
argument_list|(
literal|"Chapter Title"
argument_list|)
operator|==
literal|0
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"chaptertitle"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|indexOf
argument_list|(
literal|"Source"
argument_list|)
operator|==
literal|0
condition|)
block|{
name|Matcher
name|matcher
decl_stmt|;
if|if
condition|(
operator|(
name|matcher
operator|=
name|OvidImporter
operator|.
name|ovid_src_pat
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
operator|)
operator|.
name|find
argument_list|()
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"issue"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|5
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|matcher
operator|=
name|OvidImporter
operator|.
name|ovid_src_pat_no_issue
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
operator|)
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// may be missing the issue
name|h
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|4
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|matcher
operator|=
name|OvidImporter
operator|.
name|ovid_src_pat_2
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
operator|)
operator|.
name|find
argument_list|()
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"issue"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"month"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|6
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|matcher
operator|=
name|OvidImporter
operator|.
name|incollection_pat
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
operator|)
operator|.
name|find
argument_list|()
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"editor"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|" \\(Ed\\)"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"booktitle"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"address"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"publisher"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|6
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|matcher
operator|=
name|OvidImporter
operator|.
name|book_pat
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
operator|)
operator|.
name|find
argument_list|()
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"address"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"publisher"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|4
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Add double hyphens to page ranges:
if|if
condition|(
name|h
operator|.
name|get
argument_list|(
literal|"pages"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|h
operator|.
name|get
argument_list|(
literal|"pages"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"-"
argument_list|,
literal|"--"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
literal|"Abstract"
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
literal|"Publication Type"
argument_list|)
condition|)
block|{
if|if
condition|(
name|content
operator|.
name|contains
argument_list|(
literal|"Book"
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"entrytype"
argument_list|,
literal|"book"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|content
operator|.
name|contains
argument_list|(
literal|"Journal"
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"entrytype"
argument_list|,
literal|"article"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|content
operator|.
name|contains
argument_list|(
literal|"Conference Paper"
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"entrytype"
argument_list|,
literal|"inproceedings"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Now we need to check if a book entry has given editors in the author field;
comment|// if so, rearrange:
name|String
name|auth
init|=
name|h
operator|.
name|get
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
if|if
condition|(
name|auth
operator|!=
literal|null
operator|&&
name|auth
operator|.
name|contains
argument_list|(
literal|" [Ed]"
argument_list|)
condition|)
block|{
name|h
operator|.
name|remove
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"editor"
argument_list|,
name|auth
operator|.
name|replaceAll
argument_list|(
literal|" \\[Ed\\]"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Rearrange names properly:
name|auth
operator|=
name|h
operator|.
name|get
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
if|if
condition|(
name|auth
operator|!=
literal|null
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|fixNames
argument_list|(
name|auth
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|auth
operator|=
name|h
operator|.
name|get
argument_list|(
literal|"editor"
argument_list|)
expr_stmt|;
if|if
condition|(
name|auth
operator|!=
literal|null
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"editor"
argument_list|,
name|fixNames
argument_list|(
name|auth
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Set the entrytype properly:
name|String
name|entryType
init|=
name|h
operator|.
name|containsKey
argument_list|(
literal|"entrytype"
argument_list|)
condition|?
name|h
operator|.
name|get
argument_list|(
literal|"entrytype"
argument_list|)
else|:
literal|"other"
decl_stmt|;
name|h
operator|.
name|remove
argument_list|(
literal|"entrytype"
argument_list|)
expr_stmt|;
if|if
condition|(
name|entryType
operator|.
name|equals
argument_list|(
literal|"book"
argument_list|)
condition|)
block|{
if|if
condition|(
name|h
operator|.
name|containsKey
argument_list|(
literal|"chaptertitle"
argument_list|)
condition|)
block|{
comment|// This means we have an "incollection" entry.
name|entryType
operator|=
literal|"incollection"
expr_stmt|;
comment|// Move the "chaptertitle" to just "title":
name|h
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|h
operator|.
name|remove
argument_list|(
literal|"chaptertitle"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|BibtexEntry
name|b
init|=
operator|new
name|BibtexEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|Globals
operator|.
name|getEntryType
argument_list|(
name|entryType
argument_list|)
argument_list|)
decl_stmt|;
name|b
operator|.
name|setField
argument_list|(
name|h
argument_list|)
expr_stmt|;
name|bibitems
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
return|return
name|bibitems
return|;
block|}
comment|/**      * Convert a string of author names into a BibTeX-compatible format.      * @param content The name string.      * @return The formatted names.      */
DECL|method|fixNames (String content)
specifier|private
name|String
name|fixNames
parameter_list|(
name|String
name|content
parameter_list|)
block|{
name|String
name|names
decl_stmt|;
if|if
condition|(
name|content
operator|.
name|indexOf
argument_list|(
literal|";"
argument_list|)
operator|>
literal|0
condition|)
block|{
comment|//LN FN; [LN FN;]*
name|names
operator|=
name|content
operator|.
name|replaceAll
argument_list|(
literal|"[^\\.A-Za-z,;\\- ]"
argument_list|,
literal|""
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|";"
argument_list|,
literal|" and"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|content
operator|.
name|indexOf
argument_list|(
literal|"  "
argument_list|)
operator|>
literal|0
condition|)
block|{
name|String
index|[]
name|sNames
init|=
name|content
operator|.
name|split
argument_list|(
literal|"  "
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
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
name|sNames
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|sNames
index|[
name|i
index|]
operator|.
name|replaceFirst
argument_list|(
literal|" "
argument_list|,
literal|", "
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|names
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|names
operator|=
name|content
expr_stmt|;
block|}
return|return
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|names
argument_list|)
return|;
block|}
block|}
end_class

end_unit

