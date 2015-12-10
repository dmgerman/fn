begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
operator|.
name|SearchQuery
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
name|importer
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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

begin_comment
comment|/**  * @author Silberer, Zirn  */
end_comment

begin_class
DECL|class|SearchManagerNoGUI
class|class
name|SearchManagerNoGUI
block|{
DECL|field|searchTerm
specifier|private
name|String
name|searchTerm
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibDatabase
name|database
decl_stmt|;
DECL|field|base
specifier|private
name|BibDatabase
name|base
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
name|SearchManagerNoGUI
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|SearchManagerNoGUI (String term, BibDatabase dataBase)
specifier|public
name|SearchManagerNoGUI
parameter_list|(
name|String
name|term
parameter_list|,
name|BibDatabase
name|dataBase
parameter_list|)
block|{
name|searchTerm
operator|=
name|term
expr_stmt|;
name|database
operator|=
name|dataBase
expr_stmt|;
block|}
DECL|method|getDBfromMatches ()
specifier|public
name|BibDatabase
name|getDBfromMatches
parameter_list|()
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Search term: "
operator|+
name|searchTerm
argument_list|)
expr_stmt|;
if|if
condition|(
name|specifiedYears
argument_list|()
condition|)
block|{
name|searchTerm
operator|=
name|fieldYear
argument_list|()
expr_stmt|;
block|}
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
name|searchTerm
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_CASE_SENSITIVE
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_REG_EXP
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|searchQuery
operator|.
name|isValidQuery
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Search failed: illegal search expression"
argument_list|)
expr_stmt|;
return|return
name|base
return|;
block|}
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|database
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|Vector
argument_list|<
name|BibEntry
argument_list|>
name|matchEntries
init|=
operator|new
name|Vector
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|boolean
name|hit
init|=
name|searchQuery
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setSearchHit
argument_list|(
name|hit
argument_list|)
expr_stmt|;
if|if
condition|(
name|hit
condition|)
block|{
name|matchEntries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
name|base
operator|=
name|ImportFormatReader
operator|.
name|createDatabase
argument_list|(
name|matchEntries
argument_list|)
expr_stmt|;
return|return
name|base
return|;
block|}
DECL|method|specifiedYears ()
specifier|private
name|boolean
name|specifiedYears
parameter_list|()
block|{
return|return
name|searchTerm
operator|.
name|matches
argument_list|(
literal|"year=[0-9]{4}-[0-9]{4}"
argument_list|)
return|;
block|}
DECL|method|fieldYear ()
specifier|private
name|String
name|fieldYear
parameter_list|()
block|{
name|String
name|regPt1
init|=
literal|""
decl_stmt|;
name|String
name|regPt2
init|=
literal|""
decl_stmt|;
name|String
name|completeReg
init|=
literal|null
decl_stmt|;
name|boolean
name|reg1Set
init|=
literal|false
decl_stmt|;
comment|//if beginning of timeframe is BEFORE and end of timeframe is AFTER turn of the century
name|boolean
name|reg2Set
init|=
literal|false
decl_stmt|;
name|String
index|[]
name|searchTermsToPr
init|=
name|searchTerm
operator|.
name|split
argument_list|(
literal|"="
argument_list|)
decl_stmt|;
name|String
name|field
init|=
name|searchTermsToPr
index|[
literal|0
index|]
decl_stmt|;
name|String
index|[]
name|years
init|=
name|searchTermsToPr
index|[
literal|1
index|]
operator|.
name|split
argument_list|(
literal|"-"
argument_list|)
decl_stmt|;
name|int
name|year1
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|years
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|int
name|year2
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|years
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|year1
operator|<
literal|2000
operator|)
operator|&&
operator|(
name|year2
operator|>=
literal|2000
operator|)
condition|)
block|{
comment|//for 199.
name|regPt1
operator|=
literal|"199+["
operator|+
name|years
index|[
literal|0
index|]
operator|.
name|substring
argument_list|(
literal|3
argument_list|,
literal|4
argument_list|)
operator|+
literal|"-9]"
expr_stmt|;
name|reg1Set
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|year1
operator|<
literal|2000
condition|)
block|{
comment|// @formatter:off
name|regPt1
operator|=
literal|"199+["
operator|+
name|years
index|[
literal|0
index|]
operator|.
name|substring
argument_list|(
literal|3
argument_list|,
literal|4
argument_list|)
operator|+
literal|"-"
operator|+
name|Math
operator|.
name|min
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|years
index|[
literal|1
index|]
operator|.
name|substring
argument_list|(
literal|3
argument_list|,
literal|4
argument_list|)
argument_list|)
argument_list|,
literal|9
argument_list|)
operator|+
literal|"]"
expr_stmt|;
name|reg1Set
operator|=
literal|true
expr_stmt|;
comment|// @formatter:on
block|}
block|}
if|if
condition|(
operator|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|years
index|[
literal|1
index|]
argument_list|)
operator|>=
literal|2000
operator|)
operator|&&
operator|(
name|year1
operator|<
literal|2000
operator|)
condition|)
block|{
comment|//for 200.
name|regPt2
operator|=
literal|"200+[0-"
operator|+
name|years
index|[
literal|1
index|]
operator|.
name|substring
argument_list|(
literal|3
argument_list|,
literal|4
argument_list|)
operator|+
literal|"]"
expr_stmt|;
name|reg2Set
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|year2
operator|>=
literal|2000
condition|)
block|{
comment|// @formatter:off
name|regPt2
operator|=
literal|"200+["
operator|+
name|years
index|[
literal|0
index|]
operator|.
name|substring
argument_list|(
literal|3
argument_list|,
literal|4
argument_list|)
operator|+
literal|"-"
operator|+
name|Math
operator|.
name|min
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|years
index|[
literal|1
index|]
operator|.
name|substring
argument_list|(
literal|3
argument_list|,
literal|4
argument_list|)
argument_list|)
argument_list|,
literal|9
argument_list|)
operator|+
literal|"]"
expr_stmt|;
comment|// @formatter:on
name|reg2Set
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|reg1Set
operator|&&
name|reg2Set
condition|)
block|{
name|completeReg
operator|=
name|field
operator|+
literal|"="
operator|+
name|regPt1
operator|+
literal|"|"
operator|+
name|regPt2
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|reg1Set
condition|)
block|{
name|completeReg
operator|=
name|field
operator|+
literal|"="
operator|+
name|regPt1
expr_stmt|;
block|}
if|if
condition|(
name|reg2Set
condition|)
block|{
name|completeReg
operator|=
name|field
operator|+
literal|"="
operator|+
name|regPt2
expr_stmt|;
block|}
block|}
return|return
name|completeReg
return|;
block|}
block|}
end_class

end_unit

