begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
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
name|Util
import|;
end_import

begin_comment
comment|/**  * Subclass of BibtexEntry for representing entries referenced in a document that can't  * be found in JabRef's current database.  */
end_comment

begin_class
DECL|class|UndefinedBibtexEntry
specifier|public
class|class
name|UndefinedBibtexEntry
extends|extends
name|BibtexEntry
block|{
DECL|field|key
specifier|private
specifier|final
name|String
name|key
decl_stmt|;
DECL|method|UndefinedBibtexEntry (String key)
specifier|public
name|UndefinedBibtexEntry
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|super
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|key
operator|=
name|key
expr_stmt|;
name|setField
argument_list|(
literal|"author"
argument_list|,
name|OOBibStyle
operator|.
name|UNDEFINED_CITATION_MARKER
argument_list|)
expr_stmt|;
block|}
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|key
return|;
block|}
block|}
end_class

end_unit

