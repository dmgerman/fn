begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|MouseEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuItem
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPopupMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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

begin_comment
comment|/**  * Adds popup functionality to DragDropPane   *   * Code inspired by http://forums.devx.com/showthread.php?t=151270  */
end_comment

begin_class
DECL|class|DragDropPopupPane
specifier|public
class|class
name|DragDropPopupPane
extends|extends
name|DragDropPane
block|{
DECL|field|popupMenu
specifier|private
name|JPopupMenu
name|popupMenu
decl_stmt|;
DECL|method|DragDropPopupPane (AbstractAction manageSelectorsAction, AbstractAction databasePropertiesAction, AbstractAction bibtexKeyPatternAction)
specifier|public
name|DragDropPopupPane
parameter_list|(
name|AbstractAction
name|manageSelectorsAction
parameter_list|,
name|AbstractAction
name|databasePropertiesAction
parameter_list|,
name|AbstractAction
name|bibtexKeyPatternAction
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|addMouseListener
argument_list|(
operator|new
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|MouseAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|tabClicked
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|initPopupMenu
argument_list|(
name|manageSelectorsAction
argument_list|,
name|databasePropertiesAction
argument_list|,
name|bibtexKeyPatternAction
argument_list|)
expr_stmt|;
block|}
DECL|method|initPopupMenu (AbstractAction manageSelectorsAction, AbstractAction databasePropertiesAction, AbstractAction bibtexKeyPatternAction)
specifier|private
name|void
name|initPopupMenu
parameter_list|(
name|AbstractAction
name|manageSelectorsAction
parameter_list|,
name|AbstractAction
name|databasePropertiesAction
parameter_list|,
name|AbstractAction
name|bibtexKeyPatternAction
parameter_list|)
block|{
name|popupMenu
operator|=
operator|new
name|JPopupMenu
argument_list|()
expr_stmt|;
name|JMenuItem
name|databasePropertiesBtn
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Database properties"
argument_list|)
argument_list|)
decl_stmt|;
name|databasePropertiesBtn
operator|.
name|addActionListener
argument_list|(
name|databasePropertiesAction
argument_list|)
expr_stmt|;
name|popupMenu
operator|.
name|add
argument_list|(
name|databasePropertiesBtn
argument_list|)
expr_stmt|;
name|JMenuItem
name|bibtexKeyPatternBtn
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Bibtex key patterns"
argument_list|)
argument_list|)
decl_stmt|;
name|bibtexKeyPatternBtn
operator|.
name|addActionListener
argument_list|(
name|bibtexKeyPatternAction
argument_list|)
expr_stmt|;
name|popupMenu
operator|.
name|add
argument_list|(
name|bibtexKeyPatternBtn
argument_list|)
expr_stmt|;
name|JMenuItem
name|manageSelectorsBtn
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage content selectors"
argument_list|)
argument_list|)
decl_stmt|;
name|manageSelectorsBtn
operator|.
name|addActionListener
argument_list|(
name|manageSelectorsAction
argument_list|)
expr_stmt|;
name|popupMenu
operator|.
name|add
argument_list|(
name|manageSelectorsBtn
argument_list|)
expr_stmt|;
name|JMenuItem
name|closeBtn
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Close"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"close"
argument_list|)
argument_list|)
decl_stmt|;
name|closeBtn
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|closeSelectedTab
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|popupMenu
operator|.
name|add
argument_list|(
name|closeBtn
argument_list|)
expr_stmt|;
block|}
DECL|method|tabClicked (MouseEvent e)
specifier|private
name|void
name|tabClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getButton
argument_list|()
operator|!=
name|MouseEvent
operator|.
name|BUTTON1
operator|&&
name|e
operator|.
name|getClickCount
argument_list|()
operator|==
literal|1
condition|)
block|{
comment|// if is right-click
comment|// display popup near location of mouse click
name|popupMenu
operator|.
name|show
argument_list|(
name|e
operator|.
name|getComponent
argument_list|()
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
operator|-
literal|10
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|closeSelectedTab ()
specifier|private
name|void
name|closeSelectedTab
parameter_list|()
block|{
comment|// remove selected tab
name|remove
argument_list|(
name|getSelectedIndex
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

