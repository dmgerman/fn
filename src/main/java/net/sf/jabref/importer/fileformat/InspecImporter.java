begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
package|;
end_package

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
name|importer
operator|.
name|ImportFormatReader
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
name|importer
operator|.
name|OutputPrinter
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
name|AuthorList
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
operator|.
name|EntryTypes
import|;
end_import

begin_comment
comment|/**  * INSPEC format importer.  */
end_comment

begin_class
DECL|class|InspecImporter
specifier|public
class|class
name|InspecImporter
extends|extends
name|ImportFormat
block|{
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
literal|"INSPEC"
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
literal|"inspec"
return|;
block|}
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
comment|// Our strategy is to look for the "PY<year>" line.
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
comment|//Pattern pat1 = Pattern.compile("PY:  \\d{4}");
name|Pattern
name|pat1
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Record.*INSPEC.*"
argument_list|)
decl_stmt|;
comment|//was PY \\\\d{4}? before
name|String
name|str
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
condition|)
block|{
comment|//Inspec and IEEE seem to have these strange " - " between key and value
comment|//str = str.replace(" - ", "");
comment|//System.out.println(str);
if|if
condition|(
name|pat1
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
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Parse the entries in the source, and return a List of BibEntry objects.      */
annotation|@
name|Override
DECL|method|importEntries (InputStream stream, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibEntry
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
name|BibEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|String
name|str
decl_stmt|;
try|try
init|(
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
init|)
block|{
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
condition|)
block|{
if|if
condition|(
name|str
operator|.
name|length
argument_list|()
operator|<
literal|2
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|str
operator|.
name|indexOf
argument_list|(
literal|"Record"
argument_list|)
operator|==
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"__::__"
argument_list|)
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"__NEWFIELD__"
argument_list|)
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|String
index|[]
name|entries
init|=
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"__::__"
argument_list|)
decl_stmt|;
name|String
name|type
init|=
literal|""
decl_stmt|;
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
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|indexOf
argument_list|(
literal|"Record"
argument_list|)
operator|!=
literal|0
condition|)
block|{
continue|continue;
block|}
name|h
operator|.
name|clear
argument_list|()
expr_stmt|;
name|String
index|[]
name|fields
init|=
name|entry
operator|.
name|split
argument_list|(
literal|"__NEWFIELD__"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|fields
control|)
block|{
comment|//System.out.println(fields[j]);
name|String
name|f3
init|=
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
decl_stmt|;
name|String
name|frest
init|=
name|s
operator|.
name|substring
argument_list|(
literal|5
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"TI"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|frest
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PY"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|frest
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"AU"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|frest
operator|.
name|replaceAll
argument_list|(
literal|",-"
argument_list|,
literal|", "
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|";"
argument_list|,
literal|" and "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"AB"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|frest
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"ID"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|frest
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SO"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|int
name|m
init|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|>=
literal|0
condition|)
block|{
name|String
name|jr
init|=
name|frest
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|m
argument_list|)
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|jr
operator|.
name|replaceAll
argument_list|(
literal|"-"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
name|frest
operator|=
name|frest
operator|.
name|substring
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|m
operator|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|>=
literal|5
condition|)
block|{
name|String
name|yr
init|=
name|frest
operator|.
name|substring
argument_list|(
name|m
operator|-
literal|5
argument_list|,
name|m
argument_list|)
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|yr
argument_list|)
expr_stmt|;
name|frest
operator|=
name|frest
operator|.
name|substring
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|m
operator|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|>=
literal|0
condition|)
block|{
name|String
name|pg
init|=
name|frest
operator|.
name|substring
argument_list|(
name|m
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|pg
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|frest
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|m
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
elseif|else
if|if
condition|(
literal|"RT"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|frest
operator|=
name|frest
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
literal|"Journal-Paper"
operator|.
name|equals
argument_list|(
name|frest
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"article"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"Conference-Paper"
operator|.
name|equals
argument_list|(
name|frest
argument_list|)
operator|||
literal|"Conference-Paper; Journal-Paper"
operator|.
name|equals
argument_list|(
name|frest
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"inproceedings"
expr_stmt|;
block|}
else|else
block|{
name|type
operator|=
name|frest
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|BibEntry
name|b
init|=
operator|new
name|BibEntry
argument_list|(
name|DEFAULT_BIBTEXENTRY_ID
argument_list|,
name|type
argument_list|)
decl_stmt|;
comment|// id assumes an existing database so don't
comment|// create one here
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
block|}
end_class

end_unit

