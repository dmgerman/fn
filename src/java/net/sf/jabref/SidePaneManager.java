begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003  Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|util
operator|.
name|HashMap
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
name|groups
operator|.
name|GroupSelector
import|;
end_import

begin_class
DECL|class|SidePaneManager
specifier|public
class|class
name|SidePaneManager
block|{
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|sidep
name|SidePane
name|sidep
decl_stmt|;
DECL|field|prefs
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|metaData
name|MetaData
name|metaData
decl_stmt|;
DECL|field|components
name|HashMap
name|components
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|visibleComponents
specifier|private
name|int
name|visibleComponents
init|=
literal|0
decl_stmt|;
DECL|method|SidePaneManager (JabRefFrame frame, BasePanel panel, JabRefPreferences prefs, MetaData metaData)
specifier|public
name|SidePaneManager
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
block|}
DECL|method|populatePanel ()
specifier|public
name|void
name|populatePanel
parameter_list|()
block|{
name|sidep
operator|=
operator|new
name|SidePane
argument_list|(
name|panel
argument_list|)
expr_stmt|;
comment|// Groups
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"groupSelectorVisible"
argument_list|)
operator|&&
operator|(
name|metaData
operator|.
name|getData
argument_list|(
literal|"groups"
argument_list|)
operator|!=
literal|null
operator|)
condition|)
block|{
name|GroupSelector
name|gs
init|=
operator|new
name|GroupSelector
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|,
name|metaData
operator|.
name|getData
argument_list|(
literal|"groups"
argument_list|)
argument_list|,
name|this
argument_list|,
name|prefs
argument_list|)
decl_stmt|;
name|add
argument_list|(
literal|"groups"
argument_list|,
name|gs
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|components
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
name|panel
operator|.
name|setLeftComponent
argument_list|(
name|sidep
argument_list|)
expr_stmt|;
block|}
DECL|method|togglePanel (String name)
specifier|public
name|void
name|togglePanel
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
operator|(
operator|(
name|SidePaneComponent
operator|)
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
operator|)
operator|.
name|isVisible
argument_list|()
condition|)
block|{
name|visibleComponents
operator|++
expr_stmt|;
operator|(
operator|(
name|SidePaneComponent
operator|)
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
operator|)
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|visibleComponents
operator|==
literal|1
condition|)
name|panel
operator|.
name|setLeftComponent
argument_list|(
name|sidep
argument_list|)
expr_stmt|;
operator|(
operator|(
name|SidePaneComponent
operator|)
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
operator|)
operator|.
name|componentOpening
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|hideAway
argument_list|(
operator|(
name|SidePaneComponent
operator|)
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return;
comment|// Component already there.
block|}
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"groups"
argument_list|)
condition|)
block|{
if|if
condition|(
name|metaData
operator|.
name|getData
argument_list|(
literal|"groups"
argument_list|)
operator|==
literal|null
condition|)
name|metaData
operator|.
name|putData
argument_list|(
literal|"groups"
argument_list|,
operator|new
name|Vector
argument_list|()
argument_list|)
expr_stmt|;
name|GroupSelector
name|gs
init|=
operator|new
name|GroupSelector
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|,
name|metaData
operator|.
name|getData
argument_list|(
literal|"groups"
argument_list|)
argument_list|,
name|this
argument_list|,
name|prefs
argument_list|)
decl_stmt|;
name|add
argument_list|(
literal|"groups"
argument_list|,
name|gs
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|add (String name, SidePaneComponent comp)
specifier|private
specifier|synchronized
name|void
name|add
parameter_list|(
name|String
name|name
parameter_list|,
name|SidePaneComponent
name|comp
parameter_list|)
block|{
name|sidep
operator|.
name|add
argument_list|(
name|comp
argument_list|)
expr_stmt|;
name|components
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|visibleComponents
operator|++
expr_stmt|;
if|if
condition|(
name|visibleComponents
operator|==
literal|1
condition|)
name|panel
operator|.
name|setLeftComponent
argument_list|(
name|sidep
argument_list|)
expr_stmt|;
name|comp
operator|.
name|componentOpening
argument_list|()
expr_stmt|;
block|}
DECL|method|hideAway (SidePaneComponent comp)
specifier|public
specifier|synchronized
name|void
name|hideAway
parameter_list|(
name|SidePaneComponent
name|comp
parameter_list|)
block|{
name|comp
operator|.
name|componentClosing
argument_list|()
expr_stmt|;
name|comp
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|visibleComponents
operator|--
expr_stmt|;
if|if
condition|(
name|visibleComponents
operator|==
literal|0
condition|)
name|panel
operator|.
name|remove
argument_list|(
name|sidep
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

