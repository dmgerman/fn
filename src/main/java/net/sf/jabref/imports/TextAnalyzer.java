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
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|BibtexEntry
import|;
end_import

begin_class
DECL|class|TextAnalyzer
class|class
name|TextAnalyzer
block|{
DECL|field|be
specifier|private
specifier|final
name|BibtexEntry
name|be
init|=
literal|null
decl_stmt|;
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
name|TextAnalyzer
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// Needs to give a year definitely in the future.
comment|// Used for guessing the
comment|// year field when parsing textual data. :-)
DECL|field|FUTURE_YEAR
specifier|private
specifier|static
specifier|final
name|int
name|FUTURE_YEAR
init|=
literal|2050
decl_stmt|;
DECL|method|TextAnalyzer (String text)
specifier|public
name|TextAnalyzer
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|guessBibtexFields
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
DECL|method|getEntry ()
specifier|public
name|BibtexEntry
name|getEntry
parameter_list|()
block|{
return|return
name|be
return|;
block|}
DECL|method|guessBibtexFields (String text)
specifier|private
name|void
name|guessBibtexFields
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|TreeSet
argument_list|<
name|Substring
argument_list|>
name|usedParts
init|=
operator|new
name|TreeSet
argument_list|<
name|Substring
argument_list|>
argument_list|()
decl_stmt|;
name|text
operator|=
literal|"  "
operator|+
name|text
operator|+
literal|"  "
expr_stmt|;
name|String
index|[]
name|split
decl_stmt|;
comment|// Look for the year:
name|String
name|year
init|=
literal|null
decl_stmt|;
name|String
name|yearRx
init|=
literal|"(\\s|\\()\\d\\d\\d\\d(\\.|,|\\))"
decl_stmt|;
name|String
index|[]
name|cand
init|=
name|getMatches
argument_list|(
name|text
argument_list|,
name|yearRx
argument_list|)
decl_stmt|;
if|if
condition|(
name|cand
operator|.
name|length
operator|==
literal|1
condition|)
block|{
comment|// Only one four-digit number, so we guess that is the year.
name|year
operator|=
name|clean
argument_list|(
name|cand
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|int
name|pos
init|=
name|text
operator|.
name|indexOf
argument_list|(
name|year
argument_list|)
decl_stmt|;
name|usedParts
operator|.
name|add
argument_list|(
operator|new
name|Substring
argument_list|(
literal|"year"
argument_list|,
name|pos
argument_list|,
name|pos
operator|+
name|year
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Guessing 'year': '"
operator|+
name|year
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|cand
operator|.
name|length
operator|>
literal|1
condition|)
block|{
comment|// More than one four-digit numbers, so we look for one giving a reasonable year:
name|int
name|good
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|yearFound
init|=
operator|-
literal|1
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
name|cand
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|int
name|number
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|cand
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|number
operator|==
name|yearFound
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|number
operator|<
literal|2500
condition|)
block|{
if|if
condition|(
name|good
operator|==
operator|-
literal|1
condition|)
block|{
name|good
operator|=
name|i
expr_stmt|;
name|yearFound
operator|=
name|number
expr_stmt|;
block|}
else|else
block|{
comment|// More than one found. Be a bit more specific.
if|if
condition|(
name|yearFound
operator|<
name|FUTURE_YEAR
operator|&&
name|number
operator|<
name|FUTURE_YEAR
condition|)
block|{
name|good
operator|=
operator|-
literal|1
expr_stmt|;
break|break;
comment|// Give up, both seem good enough.
block|}
elseif|else
if|if
condition|(
name|yearFound
operator|>=
name|FUTURE_YEAR
operator|&&
name|number
operator|<
name|FUTURE_YEAR
condition|)
block|{
name|good
operator|=
name|i
expr_stmt|;
name|yearFound
operator|=
name|number
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
name|good
operator|>=
literal|0
condition|)
block|{
name|year
operator|=
name|clean
argument_list|(
name|cand
index|[
name|good
index|]
argument_list|)
expr_stmt|;
name|int
name|pos
init|=
name|text
operator|.
name|indexOf
argument_list|(
name|year
argument_list|)
decl_stmt|;
name|usedParts
operator|.
name|add
argument_list|(
operator|new
name|Substring
argument_list|(
literal|"year"
argument_list|,
name|pos
argument_list|,
name|pos
operator|+
name|year
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Guessing 'year': '"
operator|+
name|year
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Look for Pages:
name|String
name|pages
decl_stmt|;
name|String
name|pagesRx
init|=
literal|"\\s(\\d{1,4})( ??)-( ??)(\\d{1,4})(\\.|,|\\s)"
decl_stmt|;
name|cand
operator|=
name|getMatches
argument_list|(
name|text
argument_list|,
name|pagesRx
argument_list|)
expr_stmt|;
if|if
condition|(
name|cand
operator|.
name|length
operator|==
literal|1
condition|)
block|{
name|pages
operator|=
name|clean
argument_list|(
name|cand
index|[
literal|0
index|]
operator|.
name|replaceAll
argument_list|(
literal|"-|( - )"
argument_list|,
literal|"--"
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|pos
init|=
name|text
operator|.
name|indexOf
argument_list|(
name|cand
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|usedParts
operator|.
name|add
argument_list|(
operator|new
name|Substring
argument_list|(
literal|"pages"
argument_list|,
name|pos
argument_list|,
name|pos
operator|+
name|year
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Guessing 'pages': '"
operator|+
name|pages
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|cand
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|int
name|found
init|=
operator|-
literal|1
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
name|cand
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|split
operator|=
name|clean
argument_list|(
name|cand
index|[
name|i
index|]
operator|.
name|replaceAll
argument_list|(
literal|"\\s"
argument_list|,
literal|""
argument_list|)
argument_list|)
operator|.
name|split
argument_list|(
literal|"-"
argument_list|)
expr_stmt|;
comment|//   Util.pr("Pg: "+pages);
name|int
name|first
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|split
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|int
name|second
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|split
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|second
operator|-
name|first
operator|>
literal|3
condition|)
block|{
name|found
operator|=
name|i
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|found
operator|>=
literal|0
condition|)
block|{
name|pages
operator|=
name|clean
argument_list|(
name|cand
index|[
name|found
index|]
operator|.
name|replaceAll
argument_list|(
literal|"-|( - )"
argument_list|,
literal|"--"
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|pos
init|=
name|text
operator|.
name|indexOf
argument_list|(
name|cand
index|[
name|found
index|]
argument_list|)
decl_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Guessing 'pages': '"
operator|+
name|pages
operator|+
literal|"'"
argument_list|)
expr_stmt|;
name|usedParts
operator|.
name|add
argument_list|(
operator|new
name|Substring
argument_list|(
literal|"pages"
argument_list|,
name|pos
argument_list|,
name|pos
operator|+
name|pages
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|//String journalRx = "(\\.|\\n)\\s??([a-zA-Z\\. ]{8,30}+)((vol\\.|Vol\\.|Volume|volume))??(.??)(\\d{1,3})(\\.|,|\\s)";
name|String
name|journal
decl_stmt|;
name|String
name|volume
decl_stmt|;
name|String
name|journalRx
init|=
literal|"(,|\\.|\\n)\\s??([a-zA-Z\\. ]{8,30}+)((.){0,2})((vol\\.|Vol\\.|Volume|volume))??\\s??(\\d{1,3})(\\.|,|\\s|:)"
decl_stmt|;
name|cand
operator|=
name|getMatches
argument_list|(
name|text
argument_list|,
name|journalRx
argument_list|)
expr_stmt|;
if|if
condition|(
name|cand
operator|.
name|length
operator|>
literal|0
condition|)
block|{
comment|//Util.pr("guessing 'journal': '" + cand[0] + "'");
name|cand
index|[
literal|0
index|]
operator|=
name|cand
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
name|int
name|pos
init|=
name|cand
index|[
literal|0
index|]
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|)
decl_stmt|;
if|if
condition|(
name|pos
operator|>
literal|0
condition|)
block|{
name|volume
operator|=
name|clean
argument_list|(
name|cand
index|[
literal|0
index|]
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Guessing 'volume': '"
operator|+
name|volume
operator|+
literal|"'"
argument_list|)
expr_stmt|;
name|journal
operator|=
name|clean
argument_list|(
name|cand
index|[
literal|0
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|pos
argument_list|)
argument_list|)
expr_stmt|;
comment|//Util.pr("guessing 'journal': '" + journal + "'");
name|pos
operator|=
name|journal
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
if|if
condition|(
name|pos
operator|>
literal|0
condition|)
block|{
name|String
name|last
init|=
name|journal
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
name|last
operator|.
name|equals
argument_list|(
literal|"volume"
argument_list|)
operator|||
name|last
operator|.
name|equals
argument_list|(
literal|"vol"
argument_list|)
operator|||
name|last
operator|.
name|equals
argument_list|(
literal|"v"
argument_list|)
condition|)
block|{
name|journal
operator|=
name|clean
argument_list|(
name|journal
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|pos
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|pos
operator|=
name|text
operator|.
name|indexOf
argument_list|(
name|journal
argument_list|)
expr_stmt|;
name|usedParts
operator|.
name|add
argument_list|(
operator|new
name|Substring
argument_list|(
literal|"journal"
argument_list|,
name|pos
argument_list|,
name|pos
operator|+
name|journal
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Guessing 'journal': '"
operator|+
name|journal
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
comment|//Util.pr("Journal? '"+cand[0]+"'");
block|}
else|else
block|{
comment|// No journal found. Maybe the year precedes the volume? Try another regexp:
block|}
comment|// Then try to find title and authors.
name|Substring
name|ss
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|free
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|piv
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Substring
name|usedPart
range|:
name|usedParts
control|)
block|{
name|ss
operator|=
name|usedPart
expr_stmt|;
if|if
condition|(
name|ss
operator|.
name|begin
argument_list|()
operator|-
name|piv
operator|>
literal|10
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"... "
operator|+
name|text
operator|.
name|substring
argument_list|(
name|piv
argument_list|,
name|ss
operator|.
name|begin
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|free
operator|.
name|add
argument_list|(
name|clean
argument_list|(
name|text
operator|.
name|substring
argument_list|(
name|piv
argument_list|,
name|ss
operator|.
name|begin
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|piv
operator|=
name|ss
operator|.
name|end
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|text
operator|.
name|length
argument_list|()
operator|-
name|piv
operator|>
literal|10
condition|)
block|{
name|free
operator|.
name|add
argument_list|(
name|clean
argument_list|(
name|text
operator|.
name|substring
argument_list|(
name|piv
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Free parts:"
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|s
range|:
name|free
control|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|": '"
operator|+
name|s
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getMatches (String text, String regexp)
specifier|private
name|String
index|[]
name|getMatches
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|regexp
parameter_list|)
block|{
name|int
name|piv
init|=
literal|0
decl_stmt|;
name|String
index|[]
name|test
init|=
name|text
operator|.
name|split
argument_list|(
name|regexp
argument_list|)
decl_stmt|;
if|if
condition|(
name|test
operator|.
name|length
operator|<
literal|2
condition|)
block|{
return|return
operator|new
name|String
index|[
literal|0
index|]
return|;
block|}
name|String
index|[]
name|out
init|=
operator|new
name|String
index|[
name|test
operator|.
name|length
operator|-
literal|1
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
name|out
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|curr
init|=
name|text
operator|.
name|split
argument_list|(
name|regexp
argument_list|,
name|i
operator|+
literal|2
argument_list|)
decl_stmt|;
name|out
index|[
name|i
index|]
operator|=
name|text
operator|.
name|substring
argument_list|(
name|piv
operator|+
name|curr
index|[
name|i
index|]
operator|.
name|length
argument_list|()
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|curr
index|[
name|i
operator|+
literal|1
index|]
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|piv
operator|+=
name|curr
index|[
name|i
index|]
operator|.
name|length
argument_list|()
operator|+
name|out
index|[
name|i
index|]
operator|.
name|length
argument_list|()
expr_stmt|;
comment|//Util.pr("--"+out[i]+"\n-> "+piv);
block|}
return|return
name|out
return|;
block|}
DECL|method|clean (String s)
specifier|private
name|String
name|clean
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
name|int
name|left
init|=
literal|0
decl_stmt|;
name|int
name|right
init|=
name|s
operator|.
name|length
argument_list|()
operator|-
literal|1
decl_stmt|;
while|while
condition|(
operator|!
name|found
operator|&&
name|left
operator|<
name|s
operator|.
name|length
argument_list|()
condition|)
block|{
name|char
name|c
init|=
name|s
operator|.
name|charAt
argument_list|(
name|left
argument_list|)
decl_stmt|;
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
operator|||
name|c
operator|==
literal|'.'
operator|||
name|c
operator|==
literal|','
operator|||
name|c
operator|==
literal|'('
operator|||
name|c
operator|==
literal|':'
operator|||
name|c
operator|==
literal|')'
condition|)
block|{
name|left
operator|++
expr_stmt|;
block|}
else|else
block|{
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|found
operator|=
literal|false
expr_stmt|;
while|while
condition|(
operator|!
name|found
operator|&&
name|right
operator|>
name|left
condition|)
block|{
name|char
name|c
init|=
name|s
operator|.
name|charAt
argument_list|(
name|right
argument_list|)
decl_stmt|;
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
operator|||
name|c
operator|==
literal|'.'
operator|||
name|c
operator|==
literal|','
operator|||
name|c
operator|==
literal|')'
operator|||
name|c
operator|==
literal|':'
operator|||
name|c
operator|==
literal|'('
condition|)
block|{
name|right
operator|--
expr_stmt|;
block|}
else|else
block|{
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
comment|//Util.pr(s+"\n"+left+" "+right);
return|return
name|s
operator|.
name|substring
argument_list|(
name|left
argument_list|,
name|Math
operator|.
name|min
argument_list|(
name|right
operator|+
literal|1
argument_list|,
name|s
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
DECL|class|Substring
specifier|private
class|class
name|Substring
implements|implements
name|Comparable
argument_list|<
name|Substring
argument_list|>
block|{
DECL|field|begin
specifier|final
name|int
name|begin
decl_stmt|;
DECL|field|end
specifier|final
name|int
name|end
decl_stmt|;
DECL|method|Substring (String name, int begin, int end)
specifier|public
name|Substring
parameter_list|(
name|String
name|name
parameter_list|,
name|int
name|begin
parameter_list|,
name|int
name|end
parameter_list|)
block|{
name|this
operator|.
name|begin
operator|=
name|begin
expr_stmt|;
name|this
operator|.
name|end
operator|=
name|end
expr_stmt|;
block|}
DECL|method|begin ()
specifier|public
name|int
name|begin
parameter_list|()
block|{
return|return
name|begin
return|;
block|}
DECL|method|end ()
specifier|public
name|int
name|end
parameter_list|()
block|{
return|return
name|end
return|;
block|}
annotation|@
name|Override
DECL|method|compareTo (Substring other)
specifier|public
name|int
name|compareTo
parameter_list|(
name|Substring
name|other
parameter_list|)
block|{
return|return
operator|new
name|Integer
argument_list|(
name|begin
argument_list|)
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|begin
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

