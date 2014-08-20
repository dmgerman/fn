begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2014 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|journals
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
name|AbstractWorker
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
name|undo
operator|.
name|NamedCompound
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Sep 17, 2005  * Time: 12:48:23 AM  * To browseOld this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|UnabbreviateAction
specifier|public
class|class
name|UnabbreviateAction
extends|extends
name|AbstractWorker
block|{
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|message
name|String
name|message
init|=
literal|""
decl_stmt|;
DECL|method|UnabbreviateAction (BasePanel panel)
specifier|public
name|UnabbreviateAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
comment|//  new FieldWeightDialog(frame).setVisible(true);
name|panel
operator|.
name|output
argument_list|(
literal|"Unabbreviating..."
argument_list|)
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|//net.sf.jabref.journals.JournalList.downloadJournalList(frame);
name|BibtexEntry
index|[]
name|entries
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
if|if
condition|(
name|entries
operator|==
literal|null
condition|)
return|return;
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"Unabbreviate journal names"
argument_list|)
decl_stmt|;
name|int
name|count
init|=
literal|0
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|Globals
operator|.
name|journalAbbrev
operator|.
name|unabbreviate
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|entry
argument_list|,
literal|"journal"
argument_list|,
name|ce
argument_list|)
condition|)
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|journalAbbrev
operator|.
name|unabbreviate
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|entry
argument_list|,
literal|"journaltitle"
argument_list|,
name|ce
argument_list|)
condition|)
name|count
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|count
operator|>
literal|0
condition|)
block|{
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|message
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unabbreviated %0 journal names."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|count
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|message
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"No journal names could be unabbreviated."
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|panel
operator|.
name|output
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

