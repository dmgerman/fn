begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
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
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingConstants
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
name|gui
operator|.
name|IconTheme
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
name|gui
operator|.
name|SidePaneComponent
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
name|gui
operator|.
name|SidePaneManager
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

begin_class
DECL|class|FileUpdatePanel
specifier|public
class|class
name|FileUpdatePanel
extends|extends
name|SidePaneComponent
implements|implements
name|ActionListener
implements|,
name|ChangeScanner
operator|.
name|DisplayResultCallback
block|{
DECL|field|NAME
specifier|public
specifier|static
specifier|final
name|String
name|NAME
init|=
literal|"fileUpdate"
decl_stmt|;
DECL|field|manager
specifier|private
specifier|final
name|SidePaneManager
name|manager
decl_stmt|;
DECL|field|scanner
specifier|private
specifier|final
name|ChangeScanner
name|scanner
decl_stmt|;
DECL|method|FileUpdatePanel (BasePanel panel, SidePaneManager manager, File file, ChangeScanner scanner)
specifier|public
name|FileUpdatePanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|SidePaneManager
name|manager
parameter_list|,
name|File
name|file
parameter_list|,
name|ChangeScanner
name|scanner
parameter_list|)
block|{
name|super
argument_list|(
name|manager
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|SAVE
operator|.
name|getIcon
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File changed"
argument_list|)
argument_list|)
expr_stmt|;
name|close
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|manager
operator|=
name|manager
expr_stmt|;
name|this
operator|.
name|scanner
operator|=
name|scanner
expr_stmt|;
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|JLabel
name|message
init|=
operator|new
name|JLabel
argument_list|(
literal|"<html><center>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"The file<BR>'%0'<BR>has been modified<BR>externally!"
argument_list|,
name|file
operator|.
name|getName
argument_list|()
argument_list|)
operator|+
literal|"</center></html>"
argument_list|,
name|SwingConstants
operator|.
name|CENTER
argument_list|)
decl_stmt|;
name|main
operator|.
name|add
argument_list|(
name|message
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|JButton
name|test
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Review changes"
argument_list|)
argument_list|)
decl_stmt|;
name|main
operator|.
name|add
argument_list|(
name|test
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|test
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * We include a getter for the BasePanel this component refers to, because this      * component needs to be closed if the BasePanel is closed.      * @return the base panel this component refers to.      */
DECL|method|getPanel ()
specifier|public
name|BasePanel
name|getPanel
parameter_list|()
block|{
return|return
name|panel
return|;
block|}
comment|/**      * Unregister when this component closes. We need that to avoid showing      * two such external change warnings at the same time, only the latest one.      */
annotation|@
name|Override
DECL|method|componentClosing ()
specifier|public
name|void
name|componentClosing
parameter_list|()
block|{
name|manager
operator|.
name|unregisterComponent
argument_list|(
name|FileUpdatePanel
operator|.
name|NAME
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getRescalingWeight ()
specifier|public
name|int
name|getRescalingWeight
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
comment|/**      * actionPerformed      *      * @param e      *            ActionEvent      */
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
comment|// ChangeScanner scanner = new ChangeScanner(frame, panel); //,
comment|// panel.database(), panel.metaData());
comment|// try {
name|scanner
operator|.
name|displayResult
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// scanner.changeScan(panel.file());
comment|// } catch (IOException ex) {
comment|// ex.printStackTrace();
comment|// }
block|}
comment|/**      * Callback method for signalling that the change scanner has displayed the      * scan results to the user.      * @param resolved true if there were no changes, or if the user has resolved them.      */
annotation|@
name|Override
DECL|method|scanResultsResolved (boolean resolved)
specifier|public
name|void
name|scanResultsResolved
parameter_list|(
name|boolean
name|resolved
parameter_list|)
block|{
if|if
condition|(
name|resolved
condition|)
block|{
name|manager
operator|.
name|hideComponent
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setUpdatedExternally
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

