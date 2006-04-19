begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.label
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|label
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
name|BibtexEntry
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
name|Globals
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
name|BibtexFields
import|;
end_import

begin_class
DECL|class|DefaultLabelRule
specifier|public
class|class
name|DefaultLabelRule
implements|implements
name|LabelRule
block|{
comment|// the default rule is that oit simply returns what it was given
DECL|method|applyRule (BibtexEntry oldEntry)
specifier|public
name|String
name|applyRule
parameter_list|(
name|BibtexEntry
name|oldEntry
parameter_list|)
block|{
return|return
operator|(
name|String
operator|)
name|oldEntry
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
return|;
block|}
block|}
end_class

end_unit

