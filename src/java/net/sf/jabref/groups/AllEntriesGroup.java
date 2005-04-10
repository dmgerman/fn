begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* All programs in this directory and subdirectories are published under the  GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it  under the terms of the GNU General Public License as published by the Free  Software Foundation; either version 2 of the License, or (at your option)  any later version.  This program is distributed in the hope that it will be useful, but WITHOUT  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for  more details.  You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc., 59  Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
package|;
end_package

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
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|AbstractUndoableEdit
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

begin_comment
comment|/**  * This group contains all entries.  */
end_comment

begin_class
DECL|class|AllEntriesGroup
specifier|public
class|class
name|AllEntriesGroup
extends|extends
name|AbstractGroup
implements|implements
name|SearchRule
block|{
DECL|field|ID
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"AllEntriesGroup:"
decl_stmt|;
DECL|method|AllEntriesGroup ()
specifier|public
name|AllEntriesGroup
parameter_list|()
block|{
name|super
argument_list|(
literal|"All Entries"
argument_list|)
expr_stmt|;
block|}
DECL|method|fromString (String s, BibtexDatabase db, int version)
specifier|public
specifier|static
name|AbstractGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|,
name|int
name|version
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
operator|!
name|s
operator|.
name|startsWith
argument_list|(
name|ID
argument_list|)
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Internal error: AllEntriesGroup cannot be created from \""
operator|+
name|s
operator|+
literal|"\""
argument_list|)
throw|;
switch|switch
condition|(
name|version
condition|)
block|{
case|case
literal|0
case|:
return|return
operator|new
name|AllEntriesGroup
argument_list|()
return|;
default|default:
throw|throw
operator|new
name|UnsupportedVersionException
argument_list|(
literal|"AllEntriesGroup"
argument_list|,
name|version
argument_list|)
throw|;
block|}
block|}
DECL|method|getSearchRule ()
specifier|public
name|SearchRule
name|getSearchRule
parameter_list|()
block|{
return|return
name|this
return|;
block|}
DECL|method|supportsAdd ()
specifier|public
name|boolean
name|supportsAdd
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
DECL|method|supportsRemove ()
specifier|public
name|boolean
name|supportsRemove
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
DECL|method|addSelection (BibtexEntry[] entries)
specifier|public
name|AbstractUndoableEdit
name|addSelection
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
comment|// not supported -> ignore
return|return
literal|null
return|;
block|}
DECL|method|addSelection (BasePanel basePanel)
specifier|public
name|AbstractUndoableEdit
name|addSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
comment|// not supported -> ignore
return|return
literal|null
return|;
block|}
DECL|method|removeSelection (BibtexEntry[] entries)
specifier|public
name|AbstractUndoableEdit
name|removeSelection
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
comment|// not supported -> ignore
return|return
literal|null
return|;
block|}
DECL|method|removeSelection (BasePanel basePanel)
specifier|public
name|AbstractUndoableEdit
name|removeSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
comment|// not supported -> ignore
return|return
literal|null
return|;
block|}
DECL|method|contains (Map searchOptions, BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|Map
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
literal|true
return|;
comment|// contains everything
block|}
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
return|return
operator|new
name|AllEntriesGroup
argument_list|()
return|;
block|}
DECL|method|applyRule (Map searchStrings, BibtexEntry bibtexEntry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
name|searchStrings
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
return|return
literal|1
return|;
comment|// contains everything
block|}
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|o
operator|instanceof
name|AllEntriesGroup
return|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|ID
return|;
block|}
DECL|method|contains (BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

