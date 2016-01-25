begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|layout
operator|.
name|format
package|;
end_package

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|layout
operator|.
name|AbstractParamLayoutFormatter
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
name|List
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

begin_comment
comment|/**  * Versatile author name formatter that takes arguments to control the formatting style.  */
end_comment

begin_class
DECL|class|Authors
specifier|public
class|class
name|Authors
extends|extends
name|AbstractParamLayoutFormatter
block|{
comment|/*     AuthorSort = [FirstFirst | LastFirst | LastFirstFirstFirst]     AuthorAbbr = [FullName | Initials | FirstInitial | MiddleInitial | InitialsNoSpace | LastName]     AuthorSep = [Comma | And | Colon | Semicolon | Sep=<string>]     AuthorLastSep = [And | Comma | Colon | Semicolon | Amp | Oxford | LastSep=<string>]     AuthorPunc = [FullPunc | NoPunc | NoComma | NoPeriod]     AuthorNumber = [inf |<number>]     AuthorNumberEtAl = [ {1} |<number>]     EtAlString = [ et al. | EtAl=<string>]      */
DECL|field|AUTHOR_ORDER
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|AUTHOR_ORDER
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|AUTHOR_ABRV
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|AUTHOR_ABRV
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|AUTHOR_PUNC
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|AUTHOR_PUNC
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|SEPARATORS
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|SEPARATORS
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|LAST_SEPARATORS
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|LAST_SEPARATORS
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|NUMBER_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|NUMBER_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[0-9]+"
argument_list|)
decl_stmt|;
static|static
block|{
name|Authors
operator|.
name|AUTHOR_ORDER
operator|.
name|add
argument_list|(
literal|"firstfirst"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_ORDER
operator|.
name|add
argument_list|(
literal|"lastfirst"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_ORDER
operator|.
name|add
argument_list|(
literal|"lastfirstfirstfirst"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_ABRV
operator|.
name|add
argument_list|(
literal|"fullname"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_ABRV
operator|.
name|add
argument_list|(
literal|"initials"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_ABRV
operator|.
name|add
argument_list|(
literal|"firstinitial"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_ABRV
operator|.
name|add
argument_list|(
literal|"middleinitial"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_ABRV
operator|.
name|add
argument_list|(
literal|"lastname"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_ABRV
operator|.
name|add
argument_list|(
literal|"initialsnospace"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_PUNC
operator|.
name|add
argument_list|(
literal|"fullpunc"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_PUNC
operator|.
name|add
argument_list|(
literal|"nopunc"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_PUNC
operator|.
name|add
argument_list|(
literal|"nocomma"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|AUTHOR_PUNC
operator|.
name|add
argument_list|(
literal|"noperiod"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|SEPARATORS
operator|.
name|add
argument_list|(
literal|"comma"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|SEPARATORS
operator|.
name|add
argument_list|(
literal|"and"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|SEPARATORS
operator|.
name|add
argument_list|(
literal|"colon"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|SEPARATORS
operator|.
name|add
argument_list|(
literal|"semicolon"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|SEPARATORS
operator|.
name|add
argument_list|(
literal|"sep"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|LAST_SEPARATORS
operator|.
name|add
argument_list|(
literal|"and"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|LAST_SEPARATORS
operator|.
name|add
argument_list|(
literal|"colon"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|LAST_SEPARATORS
operator|.
name|add
argument_list|(
literal|"semicolon"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|LAST_SEPARATORS
operator|.
name|add
argument_list|(
literal|"amp"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|LAST_SEPARATORS
operator|.
name|add
argument_list|(
literal|"oxford"
argument_list|)
expr_stmt|;
name|Authors
operator|.
name|LAST_SEPARATORS
operator|.
name|add
argument_list|(
literal|"lastsep"
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
specifier|final
name|int
DECL|field|FIRST_FIRST
name|FIRST_FIRST
init|=
literal|0
decl_stmt|;
DECL|field|LAST_FIRST
specifier|private
specifier|static
specifier|final
name|int
name|LAST_FIRST
init|=
literal|1
decl_stmt|;
DECL|field|LF_FF
specifier|private
specifier|static
specifier|final
name|int
name|LF_FF
init|=
literal|2
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
DECL|field|COMMA
name|COMMA
init|=
literal|", "
decl_stmt|;
DECL|field|AMP
specifier|private
specifier|static
specifier|final
name|String
name|AMP
init|=
literal|"& "
decl_stmt|;
DECL|field|COLON
specifier|private
specifier|static
specifier|final
name|String
name|COLON
init|=
literal|": "
decl_stmt|;
DECL|field|SEMICOLON
specifier|private
specifier|static
specifier|final
name|String
name|SEMICOLON
init|=
literal|"; "
decl_stmt|;
DECL|field|AND
specifier|private
specifier|static
specifier|final
name|String
name|AND
init|=
literal|" and "
decl_stmt|;
DECL|field|OXFORD
specifier|private
specifier|static
specifier|final
name|String
name|OXFORD
init|=
literal|", and "
decl_stmt|;
DECL|field|flMode
specifier|private
name|int
name|flMode
decl_stmt|;
specifier|private
name|boolean
DECL|field|abbreviate
name|abbreviate
init|=
literal|true
decl_stmt|;
DECL|field|firstInitialOnly
specifier|private
name|boolean
name|firstInitialOnly
decl_stmt|;
DECL|field|middleInitial
specifier|private
name|boolean
name|middleInitial
decl_stmt|;
DECL|field|lastNameOnly
specifier|private
name|boolean
name|lastNameOnly
decl_stmt|;
DECL|field|abbrDots
specifier|private
name|boolean
name|abbrDots
init|=
literal|true
decl_stmt|;
DECL|field|abbrSpaces
specifier|private
name|boolean
name|abbrSpaces
init|=
literal|true
decl_stmt|;
DECL|field|setSep
specifier|private
name|boolean
name|setSep
decl_stmt|;
DECL|field|setMaxAuthors
specifier|private
name|boolean
name|setMaxAuthors
decl_stmt|;
DECL|field|maxAuthors
specifier|private
name|int
name|maxAuthors
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|authorNumberEtAl
specifier|private
name|int
name|authorNumberEtAl
init|=
literal|1
decl_stmt|;
DECL|field|lastFirstSeparator
specifier|private
name|String
name|lastFirstSeparator
init|=
literal|", "
decl_stmt|;
DECL|field|separator
specifier|private
name|String
name|separator
init|=
name|Authors
operator|.
name|COMMA
decl_stmt|;
DECL|field|lastSeparator
specifier|private
name|String
name|lastSeparator
init|=
name|Authors
operator|.
name|AND
decl_stmt|;
DECL|field|etAlString
specifier|private
name|String
name|etAlString
init|=
literal|" et al."
decl_stmt|;
annotation|@
name|Override
DECL|method|setArgument (String arg)
specifier|public
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
block|{
name|String
index|[]
name|parts
init|=
name|AbstractParamLayoutFormatter
operator|.
name|parseArgument
argument_list|(
name|arg
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|part
range|:
name|parts
control|)
block|{
name|int
name|index
init|=
name|part
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>
literal|0
condition|)
block|{
name|String
name|key
init|=
name|part
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|part
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
decl_stmt|;
name|handleArgument
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|handleArgument
argument_list|(
name|part
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|handleArgument (String key, String value)
specifier|private
name|void
name|handleArgument
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|Authors
operator|.
name|AUTHOR_ORDER
operator|.
name|contains
argument_list|(
name|key
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"FirstFirst"
argument_list|)
condition|)
block|{
name|flMode
operator|=
name|Authors
operator|.
name|FIRST_FIRST
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"LastFirst"
argument_list|)
condition|)
block|{
name|flMode
operator|=
name|Authors
operator|.
name|LAST_FIRST
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"LastFirstFirstFirst"
argument_list|)
condition|)
block|{
name|flMode
operator|=
name|Authors
operator|.
name|LF_FF
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|Authors
operator|.
name|AUTHOR_ABRV
operator|.
name|contains
argument_list|(
name|key
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"FullName"
argument_list|)
condition|)
block|{
name|abbreviate
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"Initials"
argument_list|)
condition|)
block|{
name|abbreviate
operator|=
literal|true
expr_stmt|;
name|firstInitialOnly
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"FirstInitial"
argument_list|)
condition|)
block|{
name|abbreviate
operator|=
literal|true
expr_stmt|;
name|firstInitialOnly
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"MiddleInitial"
argument_list|)
condition|)
block|{
name|abbreviate
operator|=
literal|true
expr_stmt|;
name|middleInitial
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"LastName"
argument_list|)
condition|)
block|{
name|lastNameOnly
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"InitialsNoSpace"
argument_list|)
condition|)
block|{
name|abbreviate
operator|=
literal|true
expr_stmt|;
name|abbrSpaces
operator|=
literal|false
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|Authors
operator|.
name|AUTHOR_PUNC
operator|.
name|contains
argument_list|(
name|key
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"FullPunc"
argument_list|)
condition|)
block|{
name|abbrDots
operator|=
literal|true
expr_stmt|;
name|lastFirstSeparator
operator|=
literal|", "
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"NoPunc"
argument_list|)
condition|)
block|{
name|abbrDots
operator|=
literal|false
expr_stmt|;
name|lastFirstSeparator
operator|=
literal|" "
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"NoComma"
argument_list|)
condition|)
block|{
name|abbrDots
operator|=
literal|true
expr_stmt|;
name|lastFirstSeparator
operator|=
literal|" "
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"NoPeriod"
argument_list|)
condition|)
block|{
name|abbrDots
operator|=
literal|false
expr_stmt|;
name|lastFirstSeparator
operator|=
literal|", "
expr_stmt|;
block|}
block|}
comment|// AuthorSep = [Comma | And | Colon | Semicolon | sep=<string>]
comment|// AuthorLastSep = [And | Comma | Colon | Semicolon | Amp | Oxford | lastsep=<string>]
elseif|else
if|if
condition|(
name|Authors
operator|.
name|SEPARATORS
operator|.
name|contains
argument_list|(
name|key
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|||
name|Authors
operator|.
name|LAST_SEPARATORS
operator|.
name|contains
argument_list|(
name|key
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"Comma"
argument_list|)
condition|)
block|{
if|if
condition|(
name|setSep
condition|)
block|{
name|lastSeparator
operator|=
name|Authors
operator|.
name|COMMA
expr_stmt|;
block|}
else|else
block|{
name|separator
operator|=
name|Authors
operator|.
name|COMMA
expr_stmt|;
name|setSep
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"And"
argument_list|)
condition|)
block|{
if|if
condition|(
name|setSep
condition|)
block|{
name|lastSeparator
operator|=
name|Authors
operator|.
name|AND
expr_stmt|;
block|}
else|else
block|{
name|separator
operator|=
name|Authors
operator|.
name|AND
expr_stmt|;
name|setSep
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"Colon"
argument_list|)
condition|)
block|{
if|if
condition|(
name|setSep
condition|)
block|{
name|lastSeparator
operator|=
name|Authors
operator|.
name|COLON
expr_stmt|;
block|}
else|else
block|{
name|separator
operator|=
name|Authors
operator|.
name|COLON
expr_stmt|;
name|setSep
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"Semicolon"
argument_list|)
condition|)
block|{
if|if
condition|(
name|setSep
condition|)
block|{
name|lastSeparator
operator|=
name|Authors
operator|.
name|SEMICOLON
expr_stmt|;
block|}
else|else
block|{
name|separator
operator|=
name|Authors
operator|.
name|SEMICOLON
expr_stmt|;
name|setSep
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"Oxford"
argument_list|)
condition|)
block|{
name|lastSeparator
operator|=
name|Authors
operator|.
name|OXFORD
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"Amp"
argument_list|)
condition|)
block|{
name|lastSeparator
operator|=
name|Authors
operator|.
name|AMP
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"Sep"
argument_list|)
operator|&&
operator|!
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|separator
operator|=
name|value
expr_stmt|;
name|setSep
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|comp
argument_list|(
name|key
argument_list|,
literal|"LastSep"
argument_list|)
operator|&&
operator|!
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|lastSeparator
operator|=
name|value
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"etal"
operator|.
name|equals
argument_list|(
name|key
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|&&
operator|!
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|etAlString
operator|=
name|value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Authors
operator|.
name|NUMBER_PATTERN
operator|.
name|matcher
argument_list|(
name|key
operator|.
name|trim
argument_list|()
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
comment|// Just a number:
name|int
name|num
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|key
operator|.
name|trim
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|setMaxAuthors
condition|)
block|{
name|authorNumberEtAl
operator|=
name|num
expr_stmt|;
block|}
else|else
block|{
name|maxAuthors
operator|=
name|num
expr_stmt|;
name|setMaxAuthors
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Check for case-insensitive equality between two strings after removing      * white space at the beginning and end of the first string.      * @param one The first string - whitespace is trimmed      * @param two The second string      * @return true if the strings are deemed equal      */
DECL|method|comp (String one, String two)
specifier|private
specifier|static
name|boolean
name|comp
parameter_list|(
name|String
name|one
parameter_list|,
name|String
name|two
parameter_list|)
block|{
return|return
name|one
operator|.
name|trim
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|two
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
name|fieldText
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|maxAuthors
operator|<
literal|0
operator|)
operator|||
operator|(
name|al
operator|.
name|size
argument_list|()
operator|<=
name|maxAuthors
operator|)
condition|)
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
name|al
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|AuthorList
operator|.
name|Author
name|a
init|=
name|al
operator|.
name|getAuthor
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|addSingleName
argument_list|(
name|sb
argument_list|,
name|a
argument_list|,
operator|(
name|flMode
operator|==
name|Authors
operator|.
name|FIRST_FIRST
operator|)
operator|||
operator|(
operator|(
name|flMode
operator|==
name|Authors
operator|.
name|LF_FF
operator|)
operator|&&
operator|(
name|i
operator|>
literal|0
operator|)
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
operator|(
name|al
operator|.
name|size
argument_list|()
operator|-
literal|2
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|separator
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|i
operator|<
operator|(
name|al
operator|.
name|size
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
name|lastSeparator
argument_list|)
expr_stmt|;
block|}
block|}
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
name|Math
operator|.
name|min
argument_list|(
name|al
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|,
name|authorNumberEtAl
argument_list|)
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
name|separator
argument_list|)
expr_stmt|;
block|}
name|addSingleName
argument_list|(
name|sb
argument_list|,
name|al
operator|.
name|getAuthor
argument_list|(
name|i
argument_list|)
argument_list|,
name|flMode
operator|==
name|Authors
operator|.
name|FIRST_FIRST
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|etAlString
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|addSingleName (StringBuilder sb, AuthorList.Author a, boolean firstFirst)
specifier|private
name|void
name|addSingleName
parameter_list|(
name|StringBuilder
name|sb
parameter_list|,
name|AuthorList
operator|.
name|Author
name|a
parameter_list|,
name|boolean
name|firstFirst
parameter_list|)
block|{
name|String
name|firstNamePart
init|=
name|a
operator|.
name|getFirst
argument_list|()
decl_stmt|;
name|String
name|lastNamePart
init|=
name|a
operator|.
name|getLast
argument_list|()
decl_stmt|;
name|String
name|von
init|=
name|a
operator|.
name|getVon
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|von
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|von
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|lastNamePart
operator|=
name|von
operator|+
literal|' '
operator|+
name|lastNamePart
expr_stmt|;
block|}
name|String
name|jr
init|=
name|a
operator|.
name|getJr
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|jr
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|jr
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|jrSeparator
init|=
literal|" "
decl_stmt|;
name|lastNamePart
operator|=
name|lastNamePart
operator|+
name|jrSeparator
operator|+
name|jr
expr_stmt|;
block|}
if|if
condition|(
name|abbreviate
operator|&&
operator|(
name|firstNamePart
operator|!=
literal|null
operator|)
condition|)
block|{
name|firstNamePart
operator|=
name|a
operator|.
name|getFirstAbbr
argument_list|()
expr_stmt|;
if|if
condition|(
name|firstInitialOnly
operator|&&
operator|(
name|firstNamePart
operator|.
name|length
argument_list|()
operator|>
literal|2
operator|)
condition|)
block|{
name|firstNamePart
operator|=
name|firstNamePart
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|middleInitial
condition|)
block|{
name|String
name|abbr
init|=
name|firstNamePart
decl_stmt|;
name|firstNamePart
operator|=
name|a
operator|.
name|getFirst
argument_list|()
expr_stmt|;
name|int
name|index
init|=
name|firstNamePart
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
decl_stmt|;
comment|//System.out.println(firstNamePart);
comment|//System.out.println(index);
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|firstNamePart
operator|=
name|firstNamePart
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
operator|+
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|abbr
operator|.
name|length
argument_list|()
operator|>
literal|3
condition|)
block|{
name|firstNamePart
operator|=
name|firstNamePart
operator|+
name|abbr
operator|.
name|substring
argument_list|(
literal|3
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|abbrDots
condition|)
block|{
name|firstNamePart
operator|=
name|firstNamePart
operator|.
name|replaceAll
argument_list|(
literal|"\\."
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|abbrSpaces
condition|)
block|{
name|firstNamePart
operator|=
name|firstNamePart
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
if|if
condition|(
name|lastNameOnly
operator|||
operator|(
name|firstNamePart
operator|==
literal|null
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|lastNamePart
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|firstFirst
condition|)
block|{
name|String
name|firstFirstSeparator
init|=
literal|" "
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|firstNamePart
argument_list|)
operator|.
name|append
argument_list|(
name|firstFirstSeparator
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|lastNamePart
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|lastNamePart
argument_list|)
operator|.
name|append
argument_list|(
name|lastFirstSeparator
argument_list|)
operator|.
name|append
argument_list|(
name|firstNamePart
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

