begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors and Moritz Ringler, Simon Harrer     Copyright (C) 2015 Ocar Gustafsson, Oliver Kopp      This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.util.strings
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_comment
comment|/**  * Class with static methods for changing the case of strings and arrays of strings.  */
end_comment

begin_class
DECL|class|CaseChangers
specifier|public
class|class
name|CaseChangers
block|{
comment|/**      * Represents a word in a title of a bibtex entry.      *      * A word can be mutable vs constant ({word}) and small (a, an, the, ...) vs large.      */
DECL|class|Word
specifier|private
specifier|static
specifier|final
class|class
name|Word
block|{
DECL|field|SMALLER_WORDS
specifier|private
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|SMALLER_WORDS
decl_stmt|;
static|static
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|smallerWords
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Articles
name|smallerWords
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"a"
argument_list|,
literal|"an"
argument_list|,
literal|"the"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Prepositions
name|smallerWords
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"above"
argument_list|,
literal|"about"
argument_list|,
literal|"across"
argument_list|,
literal|"against"
argument_list|,
literal|"along"
argument_list|,
literal|"among"
argument_list|,
literal|"around"
argument_list|,
literal|"at"
argument_list|,
literal|"before"
argument_list|,
literal|"behind"
argument_list|,
literal|"below"
argument_list|,
literal|"beneath"
argument_list|,
literal|"beside"
argument_list|,
literal|"between"
argument_list|,
literal|"beyond"
argument_list|,
literal|"by"
argument_list|,
literal|"down"
argument_list|,
literal|"during"
argument_list|,
literal|"except"
argument_list|,
literal|"for"
argument_list|,
literal|"from"
argument_list|,
literal|"in"
argument_list|,
literal|"inside"
argument_list|,
literal|"into"
argument_list|,
literal|"like"
argument_list|,
literal|"near"
argument_list|,
literal|"of"
argument_list|,
literal|"off"
argument_list|,
literal|"on"
argument_list|,
literal|"onto"
argument_list|,
literal|"since"
argument_list|,
literal|"to"
argument_list|,
literal|"toward"
argument_list|,
literal|"through"
argument_list|,
literal|"under"
argument_list|,
literal|"until"
argument_list|,
literal|"up"
argument_list|,
literal|"upon"
argument_list|,
literal|"with"
argument_list|,
literal|"within"
argument_list|,
literal|"without"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Conjunctions
name|smallerWords
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"and"
argument_list|,
literal|"but"
argument_list|,
literal|"for"
argument_list|,
literal|"nor"
argument_list|,
literal|"or"
argument_list|,
literal|"so"
argument_list|,
literal|"yet"
argument_list|)
argument_list|)
expr_stmt|;
comment|// unmodifiable for thread safety
name|SMALLER_WORDS
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|smallerWords
argument_list|)
expr_stmt|;
block|}
DECL|field|word
specifier|private
name|String
name|word
decl_stmt|;
DECL|method|Word (String word)
specifier|public
name|Word
parameter_list|(
name|String
name|word
parameter_list|)
block|{
name|this
operator|.
name|word
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|word
argument_list|)
expr_stmt|;
block|}
DECL|method|isConstant ()
specifier|public
name|boolean
name|isConstant
parameter_list|()
block|{
return|return
name|word
operator|.
name|startsWith
argument_list|(
literal|"{"
argument_list|)
operator|&&
name|word
operator|.
name|endsWith
argument_list|(
literal|"}"
argument_list|)
return|;
block|}
DECL|method|isMutable ()
specifier|public
name|boolean
name|isMutable
parameter_list|()
block|{
return|return
operator|!
name|isConstant
argument_list|()
return|;
block|}
DECL|method|toUpperCase ()
specifier|public
name|void
name|toUpperCase
parameter_list|()
block|{
name|this
operator|.
name|word
operator|=
name|this
operator|.
name|word
operator|.
name|toUpperCase
argument_list|()
expr_stmt|;
block|}
DECL|method|toLowerCase ()
specifier|public
name|void
name|toLowerCase
parameter_list|()
block|{
name|this
operator|.
name|word
operator|=
name|this
operator|.
name|word
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
DECL|method|toUpperFirst ()
specifier|public
name|void
name|toUpperFirst
parameter_list|()
block|{
name|this
operator|.
name|word
operator|=
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|this
operator|.
name|word
argument_list|)
expr_stmt|;
block|}
DECL|method|isSmallerWord ()
specifier|public
name|boolean
name|isSmallerWord
parameter_list|()
block|{
return|return
name|SMALLER_WORDS
operator|.
name|contains
argument_list|(
name|this
operator|.
name|word
operator|.
name|toLowerCase
argument_list|()
argument_list|)
return|;
block|}
DECL|method|isLargerWord ()
specifier|public
name|boolean
name|isLargerWord
parameter_list|()
block|{
return|return
operator|!
name|isSmallerWord
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|word
return|;
block|}
block|}
comment|/**      * Represents a title of a bibtex entry.      */
DECL|class|Title
specifier|private
specifier|static
specifier|final
class|class
name|Title
block|{
DECL|field|words
specifier|private
specifier|final
name|List
argument_list|<
name|Word
argument_list|>
name|words
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|Title (String title)
specifier|public
name|Title
parameter_list|(
name|String
name|title
parameter_list|)
block|{
for|for
control|(
name|String
name|word
range|:
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|title
argument_list|)
operator|.
name|split
argument_list|(
literal|"\\s+"
argument_list|)
control|)
block|{
name|words
operator|.
name|add
argument_list|(
operator|new
name|Word
argument_list|(
name|word
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getWords ()
specifier|public
name|List
argument_list|<
name|Word
argument_list|>
name|getWords
parameter_list|()
block|{
return|return
name|words
return|;
block|}
DECL|method|getFirstWord ()
specifier|public
name|Optional
argument_list|<
name|Word
argument_list|>
name|getFirstWord
parameter_list|()
block|{
if|if
condition|(
name|getWords
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
name|getWords
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getLastWord ()
specifier|public
name|Optional
argument_list|<
name|Word
argument_list|>
name|getLastWord
parameter_list|()
block|{
if|if
condition|(
name|getWords
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
name|getWords
argument_list|()
operator|.
name|get
argument_list|(
name|getWords
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
return|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|words
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Word
operator|::
name|toString
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|" "
argument_list|)
argument_list|)
return|;
block|}
block|}
DECL|interface|CaseChanger
specifier|public
interface|interface
name|CaseChanger
block|{
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
DECL|method|changeCase (String input)
name|String
name|changeCase
parameter_list|(
name|String
name|input
parameter_list|)
function_decl|;
block|}
DECL|class|LowerCaseChanger
specifier|public
specifier|static
class|class
name|LowerCaseChanger
implements|implements
name|CaseChanger
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"lower"
return|;
block|}
comment|/**          * Converts all characters of the string to lower case, but does not change words starting with "{"          */
annotation|@
name|Override
DECL|method|changeCase (String input)
specifier|public
name|String
name|changeCase
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|Title
name|title
init|=
operator|new
name|Title
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|title
operator|.
name|getWords
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|Word
operator|::
name|isMutable
argument_list|)
operator|.
name|forEach
argument_list|(
name|Word
operator|::
name|toLowerCase
argument_list|)
expr_stmt|;
return|return
name|title
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
DECL|class|UpperCaseChanger
specifier|public
specifier|static
class|class
name|UpperCaseChanger
implements|implements
name|CaseChanger
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"UPPER"
return|;
block|}
comment|/**          * Converts all characters of the given string to upper case, but does not change words starting with "{"          */
annotation|@
name|Override
DECL|method|changeCase (String input)
specifier|public
name|String
name|changeCase
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|Title
name|title
init|=
operator|new
name|Title
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|title
operator|.
name|getWords
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|Word
operator|::
name|isMutable
argument_list|)
operator|.
name|forEach
argument_list|(
name|Word
operator|::
name|toUpperCase
argument_list|)
expr_stmt|;
return|return
name|title
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
DECL|class|UpperFirstCaseChanger
specifier|public
specifier|static
class|class
name|UpperFirstCaseChanger
implements|implements
name|CaseChanger
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Upper first"
return|;
block|}
comment|/**          * Converts the first character of the first word of the given string to a upper case (and the remaining characters of the first word to lower case), but does not change anything if word starts with "{"          */
annotation|@
name|Override
DECL|method|changeCase (String input)
specifier|public
name|String
name|changeCase
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|Title
name|title
init|=
operator|new
name|Title
argument_list|(
name|LOWER
operator|.
name|changeCase
argument_list|(
name|input
argument_list|)
argument_list|)
decl_stmt|;
name|title
operator|.
name|getWords
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|findFirst
argument_list|()
operator|.
name|filter
argument_list|(
name|Word
operator|::
name|isMutable
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|Word
operator|::
name|toUpperFirst
argument_list|)
expr_stmt|;
return|return
name|title
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
DECL|class|UpperEachFirstCaseChanger
specifier|public
specifier|static
class|class
name|UpperEachFirstCaseChanger
implements|implements
name|CaseChanger
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Upper Each First"
return|;
block|}
comment|/**          * Converts the first character of each word of the given string to a upper case (and all others to lower case), but does not change words starting with "{"          */
annotation|@
name|Override
DECL|method|changeCase (String input)
specifier|public
name|String
name|changeCase
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|Title
name|title
init|=
operator|new
name|Title
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|title
operator|.
name|getWords
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|Word
operator|::
name|isMutable
argument_list|)
operator|.
name|forEach
argument_list|(
name|Word
operator|::
name|toUpperFirst
argument_list|)
expr_stmt|;
return|return
name|title
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
DECL|class|TitleCaseChanger
specifier|public
specifier|static
class|class
name|TitleCaseChanger
implements|implements
name|CaseChanger
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Title"
return|;
block|}
comment|/**          * Converts all words to upper case, but converts articles, prepositions, and conjunctions to lower case          * Capitalizes first and last word          * Does not change words starting with "{"          */
annotation|@
name|Override
DECL|method|changeCase (String input)
specifier|public
name|String
name|changeCase
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|Title
name|title
init|=
operator|new
name|Title
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|title
operator|.
name|getWords
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|Word
operator|::
name|isMutable
argument_list|)
operator|.
name|filter
argument_list|(
name|Word
operator|::
name|isSmallerWord
argument_list|)
operator|.
name|forEach
argument_list|(
name|Word
operator|::
name|toLowerCase
argument_list|)
expr_stmt|;
name|title
operator|.
name|getWords
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|Word
operator|::
name|isMutable
argument_list|)
operator|.
name|filter
argument_list|(
name|Word
operator|::
name|isLargerWord
argument_list|)
operator|.
name|forEach
argument_list|(
name|Word
operator|::
name|toUpperFirst
argument_list|)
expr_stmt|;
name|title
operator|.
name|getFirstWord
argument_list|()
operator|.
name|filter
argument_list|(
name|Word
operator|::
name|isMutable
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|Word
operator|::
name|toUpperFirst
argument_list|)
expr_stmt|;
name|title
operator|.
name|getLastWord
argument_list|()
operator|.
name|filter
argument_list|(
name|Word
operator|::
name|isMutable
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|Word
operator|::
name|toUpperFirst
argument_list|)
expr_stmt|;
return|return
name|title
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
DECL|field|LOWER
specifier|public
specifier|static
specifier|final
name|LowerCaseChanger
name|LOWER
init|=
operator|new
name|LowerCaseChanger
argument_list|()
decl_stmt|;
DECL|field|UPPER
specifier|public
specifier|static
specifier|final
name|UpperCaseChanger
name|UPPER
init|=
operator|new
name|UpperCaseChanger
argument_list|()
decl_stmt|;
DECL|field|UPPER_FIRST
specifier|public
specifier|static
specifier|final
name|UpperFirstCaseChanger
name|UPPER_FIRST
init|=
operator|new
name|UpperFirstCaseChanger
argument_list|()
decl_stmt|;
DECL|field|UPPER_EACH_FIRST
specifier|public
specifier|static
specifier|final
name|UpperEachFirstCaseChanger
name|UPPER_EACH_FIRST
init|=
operator|new
name|UpperEachFirstCaseChanger
argument_list|()
decl_stmt|;
DECL|field|TITLE
specifier|public
specifier|static
specifier|final
name|TitleCaseChanger
name|TITLE
init|=
operator|new
name|TitleCaseChanger
argument_list|()
decl_stmt|;
DECL|field|ALL
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|CaseChanger
argument_list|>
name|ALL
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|CaseChangers
operator|.
name|LOWER
argument_list|,
name|CaseChangers
operator|.
name|UPPER
argument_list|,
name|CaseChangers
operator|.
name|UPPER_FIRST
argument_list|,
name|CaseChangers
operator|.
name|UPPER_EACH_FIRST
argument_list|,
name|CaseChangers
operator|.
name|TITLE
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

