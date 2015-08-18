begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|BasePanel
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
name|imports
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
name|imports
operator|.
name|PostOpenAction
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
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * PostOpenAction that checks whether there are warnings about duplicate BibTeX keys, and  * if so, offers to start the duplicate resolving process.  */
end_comment

begin_class
DECL|class|HandleDuplicateWarnings
specifier|public
class|class
name|HandleDuplicateWarnings
implements|implements
name|PostOpenAction
block|{
annotation|@
name|Override
DECL|method|isActionNecessary (ParserResult pr)
specifier|public
name|boolean
name|isActionNecessary
parameter_list|(
name|ParserResult
name|pr
parameter_list|)
block|{
return|return
name|pr
operator|.
name|hasDuplicateKeys
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|performAction (BasePanel panel, ParserResult pr)
specifier|public
name|void
name|performAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|pr
parameter_list|)
block|{
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
literal|null
argument_list|,
literal|"<html><p>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"This database contains one or more duplicated BibTeX keys."
argument_list|)
operator|+
literal|"</p><p>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do you want to resolve duplicate keys now?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Duplicate BibTeX key"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"resolveDuplicateKeys"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

