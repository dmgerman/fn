begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|fxml
operator|.
name|FXML
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ComboBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TextField
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
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|views
operator|.
name|ViewLoader
import|;
end_import

begin_class
DECL|class|ImportTabView
specifier|public
class|class
name|ImportTabView
extends|extends
name|AbstractPreferenceTabView
argument_list|<
name|ImportTabViewModel
argument_list|>
implements|implements
name|PreferencesTab
block|{
DECL|field|fileNamePattern
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|String
argument_list|>
name|fileNamePattern
decl_stmt|;
DECL|field|fileDirPattern
annotation|@
name|FXML
specifier|private
name|TextField
name|fileDirPattern
decl_stmt|;
DECL|method|ImportTabView (JabRefPreferences preferences)
specifier|public
name|ImportTabView
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|ViewLoader
operator|.
name|view
argument_list|(
name|this
argument_list|)
operator|.
name|root
argument_list|(
name|this
argument_list|)
operator|.
name|load
argument_list|()
expr_stmt|;
block|}
DECL|method|initialize ()
specifier|public
name|void
name|initialize
parameter_list|()
block|{
name|this
operator|.
name|viewModel
operator|=
operator|new
name|ImportTabViewModel
argument_list|(
name|dialogService
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|fileNamePattern
operator|.
name|valueProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|fileNamePatternProperty
argument_list|()
argument_list|)
expr_stmt|;
name|fileNamePattern
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|defaultFileNamePatternsProperty
argument_list|()
argument_list|)
expr_stmt|;
name|fileDirPattern
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|fileDirPatternProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

