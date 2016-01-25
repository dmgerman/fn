begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
package|;
end_package

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
name|util
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * A repository for all journal abbreviations, including add and find methods.  */
end_comment

begin_class
DECL|class|JournalAbbreviationRepository
specifier|public
class|class
name|JournalAbbreviationRepository
block|{
DECL|field|fullNameLowerCase2Abbreviation
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Abbreviation
argument_list|>
name|fullNameLowerCase2Abbreviation
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|isoLowerCase2Abbreviation
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Abbreviation
argument_list|>
name|isoLowerCase2Abbreviation
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|medlineLowerCase2Abbreviation
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Abbreviation
argument_list|>
name|medlineLowerCase2Abbreviation
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|abbreviations
specifier|private
specifier|final
name|SortedSet
argument_list|<
name|Abbreviation
argument_list|>
name|abbreviations
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
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
name|JournalAbbreviationRepository
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|readJournalListFromResource (String resource)
specifier|public
name|void
name|readJournalListFromResource
parameter_list|(
name|String
name|resource
parameter_list|)
block|{
name|AbbreviationParser
name|parser
init|=
operator|new
name|AbbreviationParser
argument_list|()
decl_stmt|;
name|parser
operator|.
name|readJournalListFromResource
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|resource
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Abbreviation
name|abbreviation
range|:
name|parser
operator|.
name|getAbbreviations
argument_list|()
control|)
block|{
name|addEntry
argument_list|(
name|abbreviation
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|readJournalListFromFile (File file)
specifier|public
name|void
name|readJournalListFromFile
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|FileNotFoundException
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Reading journal list from file "
operator|+
name|file
argument_list|)
expr_stmt|;
name|AbbreviationParser
name|parser
init|=
operator|new
name|AbbreviationParser
argument_list|()
decl_stmt|;
name|parser
operator|.
name|readJournalListFromFile
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Abbreviation
name|abbreviation
range|:
name|parser
operator|.
name|getAbbreviations
argument_list|()
control|)
block|{
name|addEntry
argument_list|(
name|abbreviation
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|size ()
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|abbreviations
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|isKnownName (String journalName)
specifier|public
name|boolean
name|isKnownName
parameter_list|(
name|String
name|journalName
parameter_list|)
block|{
name|String
name|nameKey
init|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|journalName
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
return|return
operator|(
name|fullNameLowerCase2Abbreviation
operator|.
name|get
argument_list|(
name|nameKey
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|isoLowerCase2Abbreviation
operator|.
name|get
argument_list|(
name|nameKey
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|medlineLowerCase2Abbreviation
operator|.
name|get
argument_list|(
name|nameKey
argument_list|)
operator|!=
literal|null
operator|)
return|;
block|}
DECL|method|isAbbreviatedName (String journalName)
specifier|public
name|boolean
name|isAbbreviatedName
parameter_list|(
name|String
name|journalName
parameter_list|)
block|{
name|String
name|nameKey
init|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|journalName
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
return|return
operator|(
name|isoLowerCase2Abbreviation
operator|.
name|get
argument_list|(
name|nameKey
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|medlineLowerCase2Abbreviation
operator|.
name|get
argument_list|(
name|nameKey
argument_list|)
operator|!=
literal|null
operator|)
return|;
block|}
comment|/**      * Attempts to get the abbreviated name of the journal given. May contain dots.      *      * @param journalName The journal name to abbreviate.      * @return The abbreviated name      */
DECL|method|getAbbreviation (String journalName)
specifier|public
name|Optional
argument_list|<
name|Abbreviation
argument_list|>
name|getAbbreviation
parameter_list|(
name|String
name|journalName
parameter_list|)
block|{
name|String
name|nameKey
init|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|journalName
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|fullNameLowerCase2Abbreviation
operator|.
name|containsKey
argument_list|(
name|nameKey
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|fullNameLowerCase2Abbreviation
operator|.
name|get
argument_list|(
name|nameKey
argument_list|)
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|isoLowerCase2Abbreviation
operator|.
name|containsKey
argument_list|(
name|nameKey
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|isoLowerCase2Abbreviation
operator|.
name|get
argument_list|(
name|nameKey
argument_list|)
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|medlineLowerCase2Abbreviation
operator|.
name|containsKey
argument_list|(
name|nameKey
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|medlineLowerCase2Abbreviation
operator|.
name|get
argument_list|(
name|nameKey
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
DECL|method|addEntry (Abbreviation abbreviation)
specifier|public
name|void
name|addEntry
parameter_list|(
name|Abbreviation
name|abbreviation
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|abbreviation
argument_list|)
expr_stmt|;
if|if
condition|(
name|isKnownName
argument_list|(
name|abbreviation
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|Abbreviation
name|previous
init|=
name|getAbbreviation
argument_list|(
name|abbreviation
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
name|abbreviations
operator|.
name|remove
argument_list|(
name|previous
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Duplicate journal abbreviation - old one will be overwritten by new one\nOLD: "
operator|+
name|previous
operator|+
literal|"\nNEW: "
operator|+
name|abbreviation
argument_list|)
expr_stmt|;
block|}
name|abbreviations
operator|.
name|add
argument_list|(
name|abbreviation
argument_list|)
expr_stmt|;
name|fullNameLowerCase2Abbreviation
operator|.
name|put
argument_list|(
name|abbreviation
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|abbreviation
argument_list|)
expr_stmt|;
name|isoLowerCase2Abbreviation
operator|.
name|put
argument_list|(
name|abbreviation
operator|.
name|getIsoAbbreviation
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|abbreviation
argument_list|)
expr_stmt|;
name|medlineLowerCase2Abbreviation
operator|.
name|put
argument_list|(
name|abbreviation
operator|.
name|getMedlineAbbreviation
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|abbreviation
argument_list|)
expr_stmt|;
block|}
DECL|method|getAbbreviations ()
specifier|public
name|SortedSet
argument_list|<
name|Abbreviation
argument_list|>
name|getAbbreviations
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableSortedSet
argument_list|(
name|abbreviations
argument_list|)
return|;
block|}
DECL|method|getNextAbbreviation (String text)
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getNextAbbreviation
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|Optional
argument_list|<
name|Abbreviation
argument_list|>
name|abbreviation
init|=
name|getAbbreviation
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|abbreviation
operator|.
name|isPresent
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
name|Abbreviation
name|abbr
init|=
name|abbreviation
operator|.
name|get
argument_list|()
decl_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
name|abbr
operator|.
name|getNext
argument_list|(
name|text
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getMedlineAbbreviation (String text)
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getMedlineAbbreviation
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|Optional
argument_list|<
name|Abbreviation
argument_list|>
name|abbreviation
init|=
name|getAbbreviation
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|abbreviation
operator|.
name|isPresent
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
name|Abbreviation
name|abbr
init|=
name|abbreviation
operator|.
name|get
argument_list|()
decl_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
name|abbr
operator|.
name|getMedlineAbbreviation
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getIsoAbbreviation (String text)
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getIsoAbbreviation
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|Optional
argument_list|<
name|Abbreviation
argument_list|>
name|abbreviation
init|=
name|getAbbreviation
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|abbreviation
operator|.
name|isPresent
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
name|Abbreviation
name|abbr
init|=
name|abbreviation
operator|.
name|get
argument_list|()
decl_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
name|abbr
operator|.
name|getIsoAbbreviation
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

