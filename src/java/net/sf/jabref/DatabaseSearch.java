begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nathan Dunn, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
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
name|*
import|;
end_import

begin_class
DECL|class|DatabaseSearch
specifier|public
class|class
name|DatabaseSearch
extends|extends
name|Thread
block|{
DECL|field|panel
name|BasePanel
name|panel
init|=
literal|null
decl_stmt|;
DECL|field|thisDatabase
name|BibtexDatabase
name|thisDatabase
init|=
literal|null
decl_stmt|;
DECL|field|thisRuleSet
name|SearchRuleSet
name|thisRuleSet
init|=
literal|null
decl_stmt|;
DECL|field|thisSearchOptions
name|Hashtable
name|thisSearchOptions
init|=
literal|null
decl_stmt|;
DECL|field|thisTableModel
name|EntryTableModel
name|thisTableModel
init|=
literal|null
decl_stmt|;
DECL|field|searchValueField
name|String
name|searchValueField
init|=
literal|null
decl_stmt|;
DECL|field|reorder
DECL|field|select
DECL|field|grayOut
name|boolean
name|reorder
decl_stmt|,
name|select
decl_stmt|,
name|grayOut
decl_stmt|;
DECL|method|DatabaseSearch (Hashtable searchOptions,SearchRuleSet searchRules, BasePanel panel, String searchValueField, boolean reorder, boolean grayOut, boolean select)
specifier|public
name|DatabaseSearch
parameter_list|(
name|Hashtable
name|searchOptions
parameter_list|,
name|SearchRuleSet
name|searchRules
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|String
name|searchValueField
parameter_list|,
name|boolean
name|reorder
parameter_list|,
name|boolean
name|grayOut
parameter_list|,
name|boolean
name|select
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|thisDatabase
operator|=
name|panel
operator|.
name|getDatabase
argument_list|()
expr_stmt|;
name|thisTableModel
operator|=
name|panel
operator|.
name|getTableModel
argument_list|()
expr_stmt|;
name|thisSearchOptions
operator|=
name|searchOptions
expr_stmt|;
name|thisRuleSet
operator|=
name|searchRules
expr_stmt|;
name|this
operator|.
name|searchValueField
operator|=
name|searchValueField
expr_stmt|;
name|this
operator|.
name|reorder
operator|=
name|reorder
expr_stmt|;
name|this
operator|.
name|select
operator|=
name|select
expr_stmt|;
name|this
operator|.
name|grayOut
operator|=
name|grayOut
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|String
name|searchString
init|=
literal|null
decl_stmt|;
name|Enumeration
name|strings
init|=
name|thisSearchOptions
operator|.
name|elements
argument_list|()
decl_stmt|;
name|searchString
operator|=
operator|(
name|String
operator|)
name|strings
operator|.
name|nextElement
argument_list|()
expr_stmt|;
name|int
name|searchScore
init|=
literal|0
decl_stmt|;
name|BibtexEntry
name|bes
init|=
literal|null
decl_stmt|;
comment|//        System.out.println("doing search using this regular expr: "+searchString) ;
comment|// 0. for each field in the database
name|int
name|numRows
init|=
name|thisDatabase
operator|.
name|getEntryCount
argument_list|()
decl_stmt|,
name|hits
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|row
init|=
literal|0
init|;
name|row
operator|<
name|numRows
condition|;
name|row
operator|++
control|)
block|{
comment|// 1. search all required fields using searchString
name|bes
operator|=
name|thisDatabase
operator|.
name|getEntryById
argument_list|(
name|thisTableModel
operator|.
name|getNameFromNumber
argument_list|(
name|row
argument_list|)
argument_list|)
expr_stmt|;
comment|// 2. add score per each hit
name|searchScore
operator|=
name|thisRuleSet
operator|.
name|applyRules
argument_list|(
name|thisSearchOptions
argument_list|,
name|bes
argument_list|)
expr_stmt|;
comment|// 2.1 set score to search field
name|bes
operator|.
name|setField
argument_list|(
name|searchValueField
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|searchScore
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|searchScore
operator|>
literal|0
condition|)
name|hits
operator|++
expr_stmt|;
block|}
specifier|final
name|int
name|outputHits
init|=
name|hits
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|panel
operator|.
name|entryTable
operator|.
name|invalidate
argument_list|()
expr_stmt|;
name|panel
operator|.
name|showSearchResults
argument_list|(
name|searchValueField
argument_list|,
name|reorder
argument_list|,
name|grayOut
argument_list|,
name|select
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|searchValueField
operator|==
literal|null
operator|)
operator|||
operator|(
name|searchValueField
operator|==
name|Globals
operator|.
name|SEARCH
operator|)
condition|)
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Searched database. Global number of hits"
argument_list|)
operator|+
literal|": "
operator|+
name|outputHits
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|/*if (select) { // Select matches.           panel.selectResults(searchValueField);           new FocusRequester(panel.entryTable);         }*/
block|}
block|}
end_class

end_unit

