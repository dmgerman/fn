begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.openoffice
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|openoffice
package|;
end_package

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
name|javafx
operator|.
name|embed
operator|.
name|swing
operator|.
name|SwingNode
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|Priority
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|JabRefFrame
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|SidePaneType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|StandardActions
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|icon
operator|.
name|IconTheme
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|openoffice
operator|.
name|OpenOfficePreferences
import|;
end_import

begin_class
DECL|class|OpenOfficeSidePanel
specifier|public
class|class
name|OpenOfficeSidePanel
extends|extends
name|SidePaneComponent
block|{
DECL|field|preferences
specifier|private
name|OpenOfficePreferences
name|preferences
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|OpenOfficeSidePanel (SidePaneManager sidePaneManager, OpenOfficePreferences preferences, JabRefFrame frame)
specifier|public
name|OpenOfficeSidePanel
parameter_list|(
name|SidePaneManager
name|sidePaneManager
parameter_list|,
name|OpenOfficePreferences
name|preferences
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|sidePaneManager
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE_OPENOFFICE
argument_list|,
literal|"OpenOffice/LibreOffice"
argument_list|)
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|beforeClosing ()
specifier|public
name|void
name|beforeClosing
parameter_list|()
block|{
name|preferences
operator|.
name|setShowPanel
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|afterOpening ()
specifier|public
name|void
name|afterOpening
parameter_list|()
block|{
name|preferences
operator|.
name|setShowPanel
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getResizePolicy ()
specifier|public
name|Priority
name|getResizePolicy
parameter_list|()
block|{
return|return
name|Priority
operator|.
name|NEVER
return|;
block|}
annotation|@
name|Override
DECL|method|getToggleAction ()
specifier|public
name|Action
name|getToggleAction
parameter_list|()
block|{
return|return
name|StandardActions
operator|.
name|TOOGLE_OO
return|;
block|}
annotation|@
name|Override
DECL|method|createContentPane ()
specifier|protected
name|Node
name|createContentPane
parameter_list|()
block|{
name|SwingNode
name|swingNode
init|=
operator|new
name|SwingNode
argument_list|()
decl_stmt|;
comment|// TODO: OO, Java 9
comment|//SwingUtilities.invokeLater(() -> swingNode.setContent(new OpenOfficePanel(frame).getContent()));
return|return
name|swingNode
return|;
block|}
annotation|@
name|Override
DECL|method|getType ()
specifier|public
name|SidePaneType
name|getType
parameter_list|()
block|{
return|return
name|SidePaneType
operator|.
name|OPEN_OFFICE
return|;
block|}
block|}
end_class

end_unit

