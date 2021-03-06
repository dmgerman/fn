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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|BooleanProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleBooleanProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleStringProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|StringProperty
import|;
end_import

begin_class
DECL|class|AdvancedCiteDialogViewModel
specifier|public
class|class
name|AdvancedCiteDialogViewModel
block|{
DECL|field|pageInfo
specifier|private
specifier|final
name|StringProperty
name|pageInfo
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|citeInPar
specifier|private
specifier|final
name|BooleanProperty
name|citeInPar
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|citeInText
specifier|private
specifier|final
name|BooleanProperty
name|citeInText
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|method|pageInfoProperty ()
specifier|public
name|StringProperty
name|pageInfoProperty
parameter_list|()
block|{
return|return
name|pageInfo
return|;
block|}
DECL|method|citeInParProperty ()
specifier|public
name|BooleanProperty
name|citeInParProperty
parameter_list|()
block|{
return|return
name|citeInPar
return|;
block|}
DECL|method|citeInTextProperty ()
specifier|public
name|BooleanProperty
name|citeInTextProperty
parameter_list|()
block|{
return|return
name|citeInText
return|;
block|}
block|}
end_class

end_unit

