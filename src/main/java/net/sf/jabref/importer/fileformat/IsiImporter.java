begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|io
operator|.
name|InputStream
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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
name|ParserResult
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
name|formatter
operator|.
name|CaseChangers
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
name|MonthUtil
import|;
end_import

begin_comment
comment|/**  * Importer for the ISI Web of Science, INSPEC and Medline format.  *<p>  * Documentation about ISI WOS format:  *<p>  *<ul>  *<li>http://wos.isitrial.com/help/helpprn.html</li>  *</ul>  *<p>  *<ul>  *<li>Check compatibility with other ISI2Bib tools like:  * http://www-lab.imr.tohoku.ac.jp/~t-nissie/computer/software/isi/ or  * http://www.tug.org/tex-archive/biblio/bibtex/utils/isi2bibtex/isi2bibtex or  * http://web.mit.edu/emilio/www/utils.html</li>  *<li>Deal with capitalization correctly</li>  *</ul>  */
end_comment

begin_class
DECL|class|IsiImporter
specifier|public
class|class
name|IsiImporter
extends|extends
name|ImportFormat
block|{
DECL|field|SUB_SUP_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|SUB_SUP_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"/(sub|sup)\\s+(.*?)\\s*/"
argument_list|)
decl_stmt|;
comment|// 2006.09.05: Modified pattern to avoid false positives for other files due to an
comment|// extra | at the end:
DECL|field|ISI_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|ISI_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"FN ISI Export Format|VR 1.|PY \\d{4}"
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"ISI"
return|;
block|}
annotation|@
name|Override
DECL|method|getExtensions ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getExtensions
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
literal|"isi"
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
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
operator|)
operator|&&
operator|(
name|i
operator|<
literal|50
operator|)
condition|)
block|{
comment|/**                  * The following line gives false positives for RIS files, so it                  * should not be uncommented. The hypen is a characteristic of the                  * RIS format.                  *                  * str = str.replace(" - ", "")                  */
if|if
condition|(
name|IsiImporter
operator|.
name|ISI_PATTERN
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
block|}
return|return
literal|false
return|;
block|}
DECL|method|processSubSup (Map<String, String> map)
specifier|public
specifier|static
name|void
name|processSubSup
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
parameter_list|)
block|{
name|String
index|[]
name|subsup
init|=
block|{
literal|"title"
block|,
literal|"abstract"
block|,
literal|"review"
block|,
literal|"notes"
block|}
decl_stmt|;
for|for
control|(
name|String
name|aSubsup
range|:
name|subsup
control|)
block|{
if|if
condition|(
name|map
operator|.
name|containsKey
argument_list|(
name|aSubsup
argument_list|)
condition|)
block|{
name|Matcher
name|m
init|=
name|IsiImporter
operator|.
name|SUB_SUP_PATTERN
operator|.
name|matcher
argument_list|(
name|map
operator|.
name|get
argument_list|(
name|aSubsup
argument_list|)
argument_list|)
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|group2
init|=
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
decl_stmt|;
name|group2
operator|=
name|group2
operator|.
name|replaceAll
argument_list|(
literal|"\\$"
argument_list|,
literal|"\\\\\\\\\\\\\\$"
argument_list|)
expr_stmt|;
comment|// Escaping
comment|// insanity!
comment|// :-)
if|if
condition|(
name|group2
operator|.
name|length
argument_list|()
operator|>
literal|1
condition|)
block|{
name|group2
operator|=
literal|"{"
operator|+
name|group2
operator|+
literal|"}"
expr_stmt|;
block|}
if|if
condition|(
literal|"sub"
operator|.
name|equals
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|m
operator|.
name|appendReplacement
argument_list|(
name|sb
argument_list|,
literal|"\\$_"
operator|+
name|group2
operator|+
literal|"\\$"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|m
operator|.
name|appendReplacement
argument_list|(
name|sb
argument_list|,
literal|"\\$^"
operator|+
name|group2
operator|+
literal|"\\$"
argument_list|)
expr_stmt|;
block|}
block|}
name|m
operator|.
name|appendTail
argument_list|(
name|sb
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|aSubsup
argument_list|,
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|processCapitalization (Map<String, String> map)
specifier|private
specifier|static
name|void
name|processCapitalization
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
parameter_list|)
block|{
name|String
index|[]
name|subsup
init|=
block|{
literal|"title"
block|,
literal|"journal"
block|,
literal|"publisher"
block|}
decl_stmt|;
for|for
control|(
name|String
name|aSubsup
range|:
name|subsup
control|)
block|{
if|if
condition|(
name|map
operator|.
name|containsKey
argument_list|(
name|aSubsup
argument_list|)
condition|)
block|{
name|String
name|s
init|=
name|map
operator|.
name|get
argument_list|(
name|aSubsup
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|toUpperCase
argument_list|()
operator|.
name|equals
argument_list|(
name|s
argument_list|)
condition|)
block|{
name|s
operator|=
name|CaseChangers
operator|.
name|TO_TITLE_CASE
operator|.
name|format
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|aSubsup
argument_list|,
name|s
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|importDatabase (InputStream stream)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|InputStream
name|stream
parameter_list|)
throws|throws
name|IOException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|stream
argument_list|)
expr_stmt|;
name|List
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
comment|// Pattern fieldPattern = Pattern.compile("^AU |^TI |^SO |^DT |^C1 |^AB
comment|// |^ID |^BP |^PY |^SE |^PY |^VL |^IS ");
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
if|if
condition|(
name|str
operator|.
name|length
argument_list|()
operator|<
literal|3
condition|)
block|{
continue|continue;
block|}
comment|// beginning of a new item
if|if
condition|(
literal|"PT "
operator|.
name|equals
argument_list|(
name|str
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|3
argument_list|)
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"::"
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
name|String
name|beg
init|=
name|str
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|3
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|// I could have used the fieldPattern regular expression instead
comment|// however this seems to be
comment|// quick and dirty and it works!
if|if
condition|(
name|beg
operator|.
name|length
argument_list|()
operator|==
literal|2
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" ## "
argument_list|)
expr_stmt|;
comment|// mark the beginning of each field
name|sb
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
literal|"EOLEOL"
argument_list|)
expr_stmt|;
comment|// mark the end of each line
name|sb
operator|.
name|append
argument_list|(
name|str
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
comment|// remove the initial spaces
block|}
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
literal|"::"
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|hm
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// skip the first entry as it is either empty or has document header
for|for
control|(
name|String
name|entry
range|:
name|entries
control|)
block|{
name|String
index|[]
name|fields
init|=
name|entry
operator|.
name|split
argument_list|(
literal|" ## "
argument_list|)
decl_stmt|;
if|if
condition|(
name|fields
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|fields
operator|=
name|entry
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
name|String
name|Type
init|=
literal|""
decl_stmt|;
name|String
name|PT
init|=
literal|""
decl_stmt|;
name|String
name|pages
init|=
literal|""
decl_stmt|;
name|hm
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
comment|// empty field don't do anything
if|if
condition|(
name|field
operator|.
name|length
argument_list|()
operator|<=
literal|2
condition|)
block|{
continue|continue;
block|}
name|String
name|beg
init|=
name|field
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|field
operator|.
name|substring
argument_list|(
literal|3
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|.
name|startsWith
argument_list|(
literal|" - "
argument_list|)
condition|)
block|{
name|value
operator|=
name|value
operator|.
name|substring
argument_list|(
literal|3
argument_list|)
expr_stmt|;
block|}
name|value
operator|=
name|value
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
literal|"PT"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
if|if
condition|(
name|value
operator|.
name|startsWith
argument_list|(
literal|"J"
argument_list|)
condition|)
block|{
name|PT
operator|=
literal|"article"
expr_stmt|;
block|}
else|else
block|{
name|PT
operator|=
name|value
expr_stmt|;
block|}
name|Type
operator|=
literal|"article"
expr_stmt|;
comment|// make all of them PT?
block|}
elseif|else
if|if
condition|(
literal|"TY"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
if|if
condition|(
literal|"JOUR"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"article"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"CONF"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"inproceedings"
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"JO"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"booktitle"
argument_list|,
name|value
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
name|beg
argument_list|)
condition|)
block|{
name|String
name|author
init|=
name|IsiImporter
operator|.
name|isiAuthorsConvert
argument_list|(
name|value
operator|.
name|replace
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" and "
argument_list|)
argument_list|)
decl_stmt|;
comment|// if there is already someone there then append with "and"
if|if
condition|(
name|hm
operator|.
name|get
argument_list|(
literal|"author"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|author
operator|=
name|hm
operator|.
name|get
argument_list|(
literal|"author"
argument_list|)
operator|+
literal|" and "
operator|+
name|author
expr_stmt|;
block|}
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|author
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"TI"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|value
operator|.
name|replace
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" "
argument_list|)
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
name|beg
argument_list|)
operator|||
literal|"JA"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|value
operator|.
name|replace
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" "
argument_list|)
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
name|beg
argument_list|)
operator|||
literal|"KW"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|value
operator|=
name|value
operator|.
name|replace
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
name|String
name|existingKeywords
init|=
name|hm
operator|.
name|get
argument_list|(
literal|"keywords"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|existingKeywords
operator|==
literal|null
operator|)
operator|||
name|existingKeywords
operator|.
name|contains
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|existingKeywords
operator|=
name|value
expr_stmt|;
block|}
else|else
block|{
name|existingKeywords
operator|+=
literal|", "
operator|+
name|value
expr_stmt|;
block|}
name|hm
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|existingKeywords
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
name|beg
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|value
operator|.
name|replace
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"BP"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
operator|||
literal|"BR"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
operator|||
literal|"SP"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|pages
operator|=
name|value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"EP"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|int
name|detpos
init|=
name|value
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
decl_stmt|;
comment|// tweak for IEEE Explore
if|if
condition|(
operator|(
name|detpos
operator|!=
operator|-
literal|1
operator|)
operator|&&
operator|!
name|value
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|detpos
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|value
operator|=
name|value
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|detpos
argument_list|)
expr_stmt|;
block|}
name|pages
operator|=
name|pages
operator|+
literal|"--"
operator|+
name|value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PS"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|pages
operator|=
name|IsiImporter
operator|.
name|parsePages
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"AR"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|pages
operator|=
name|value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"IS"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"number"
argument_list|,
name|value
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
name|beg
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"VL"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PU"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"publisher"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"DI"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"doi"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PD"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|String
name|month
init|=
name|IsiImporter
operator|.
name|parseMonth
argument_list|(
name|value
argument_list|)
decl_stmt|;
if|if
condition|(
name|month
operator|!=
literal|null
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"month"
argument_list|,
name|month
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"DT"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|Type
operator|=
name|value
expr_stmt|;
if|if
condition|(
literal|"Review"
operator|.
name|equals
argument_list|(
name|Type
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"article"
expr_stmt|;
comment|// set "Review" in Note/Comment?
block|}
elseif|else
if|if
condition|(
name|Type
operator|.
name|startsWith
argument_list|(
literal|"Article"
argument_list|)
operator|||
name|Type
operator|.
name|startsWith
argument_list|(
literal|"Journal"
argument_list|)
operator|||
literal|"article"
operator|.
name|equals
argument_list|(
name|PT
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"article"
expr_stmt|;
block|}
else|else
block|{
name|Type
operator|=
literal|"misc"
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"CR"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"CitedReferences"
argument_list|,
name|value
operator|.
name|replace
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" ; "
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Preserve all other entries except
if|if
condition|(
literal|"ER"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
operator|||
literal|"EF"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
operator|||
literal|"VR"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
operator|||
literal|"FN"
operator|.
name|equals
argument_list|(
name|beg
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|hm
operator|.
name|put
argument_list|(
name|beg
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|pages
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|pages
argument_list|)
expr_stmt|;
block|}
comment|// Skip empty entries
if|if
condition|(
name|hm
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
block|}
name|BibEntry
name|b
init|=
operator|new
name|BibEntry
argument_list|(
name|DEFAULT_BIBTEXENTRY_ID
argument_list|,
name|Type
argument_list|)
decl_stmt|;
comment|// id assumes an existing database so don't
comment|// Remove empty fields:
name|List
argument_list|<
name|Object
argument_list|>
name|toRemove
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|field
range|:
name|hm
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|content
init|=
name|field
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|content
operator|==
literal|null
operator|)
operator|||
name|content
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|toRemove
operator|.
name|add
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Object
name|aToRemove
range|:
name|toRemove
control|)
block|{
name|hm
operator|.
name|remove
argument_list|(
name|aToRemove
argument_list|)
expr_stmt|;
block|}
comment|// Polish entries
name|IsiImporter
operator|.
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|IsiImporter
operator|.
name|processCapitalization
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|b
operator|.
name|setField
argument_list|(
name|hm
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
operator|new
name|ParserResult
argument_list|(
name|bibitems
argument_list|)
return|;
block|}
DECL|method|parsePages (String value)
specifier|private
specifier|static
name|String
name|parsePages
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|int
name|lastDash
init|=
name|value
operator|.
name|lastIndexOf
argument_list|(
literal|'-'
argument_list|)
decl_stmt|;
return|return
name|value
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|lastDash
argument_list|)
operator|+
literal|"--"
operator|+
name|value
operator|.
name|substring
argument_list|(
name|lastDash
operator|+
literal|1
argument_list|)
return|;
block|}
DECL|method|parseMonth (String value)
specifier|public
specifier|static
name|String
name|parseMonth
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|String
index|[]
name|parts
init|=
name|value
operator|.
name|split
argument_list|(
literal|"\\s|\\-"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|part1
range|:
name|parts
control|)
block|{
name|MonthUtil
operator|.
name|Month
name|month
init|=
name|MonthUtil
operator|.
name|getMonthByShortName
argument_list|(
name|part1
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|month
operator|.
name|isValid
argument_list|()
condition|)
block|{
return|return
name|month
operator|.
name|bibtexFormat
return|;
block|}
block|}
comment|// Try two digit month
for|for
control|(
name|String
name|part
range|:
name|parts
control|)
block|{
try|try
block|{
name|int
name|number
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|part
argument_list|)
decl_stmt|;
name|MonthUtil
operator|.
name|Month
name|month
init|=
name|MonthUtil
operator|.
name|getMonthByNumber
argument_list|(
name|number
argument_list|)
decl_stmt|;
if|if
condition|(
name|month
operator|.
name|isValid
argument_list|()
condition|)
block|{
return|return
name|month
operator|.
name|bibtexFormat
return|;
block|}
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Will expand ISI first names.      *<p>      * Fixed bug from:      * http://sourceforge.net/tracker/index.php?func=detail&aid=1542552&group_id=92314&atid=600306      */
DECL|method|isiAuthorConvert (String author)
specifier|public
specifier|static
name|String
name|isiAuthorConvert
parameter_list|(
name|String
name|author
parameter_list|)
block|{
name|String
index|[]
name|s
init|=
name|author
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|length
operator|!=
literal|2
condition|)
block|{
return|return
name|author
return|;
block|}
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|String
name|last
init|=
name|s
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|last
argument_list|)
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|String
name|first
init|=
name|s
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
index|[]
name|firstParts
init|=
name|first
operator|.
name|split
argument_list|(
literal|"\\s+"
argument_list|)
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
name|firstParts
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|first
operator|=
name|firstParts
index|[
name|i
index|]
expr_stmt|;
comment|// Do we have only uppercase chars?
if|if
condition|(
name|first
operator|.
name|toUpperCase
argument_list|()
operator|.
name|equals
argument_list|(
name|first
argument_list|)
condition|)
block|{
name|first
operator|=
name|first
operator|.
name|replace
argument_list|(
literal|"."
argument_list|,
literal|""
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
name|first
operator|.
name|length
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|first
operator|.
name|charAt
argument_list|(
name|j
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
if|if
condition|(
name|j
operator|<
operator|(
name|first
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|first
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|<
operator|(
name|firstParts
operator|.
name|length
operator|-
literal|1
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|isiAuthorsConvert (String[] authors)
specifier|private
specifier|static
name|String
index|[]
name|isiAuthorsConvert
parameter_list|(
name|String
index|[]
name|authors
parameter_list|)
block|{
name|String
index|[]
name|result
init|=
operator|new
name|String
index|[
name|authors
operator|.
name|length
index|]
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
name|result
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|result
index|[
name|i
index|]
operator|=
name|IsiImporter
operator|.
name|isiAuthorConvert
argument_list|(
name|authors
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|isiAuthorsConvert (String authors)
specifier|public
specifier|static
name|String
name|isiAuthorsConvert
parameter_list|(
name|String
name|authors
parameter_list|)
block|{
name|String
index|[]
name|s
init|=
name|IsiImporter
operator|.
name|isiAuthorsConvert
argument_list|(
name|authors
operator|.
name|split
argument_list|(
literal|" and |;"
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|String
operator|.
name|join
argument_list|(
literal|" and "
argument_list|,
name|s
argument_list|)
return|;
block|}
block|}
end_class

end_unit

