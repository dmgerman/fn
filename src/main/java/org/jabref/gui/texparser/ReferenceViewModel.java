begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.texparser
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|texparser
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringJoiner
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
name|ReadOnlyListWrapper
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|texparser
operator|.
name|Citation
import|;
end_import

begin_class
DECL|class|ReferenceViewModel
specifier|public
class|class
name|ReferenceViewModel
block|{
DECL|field|entry
specifier|private
specifier|final
name|String
name|entry
decl_stmt|;
DECL|field|citationList
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|Citation
argument_list|>
name|citationList
decl_stmt|;
DECL|method|ReferenceViewModel (String entry, Collection<Citation> citationColl)
specifier|public
name|ReferenceViewModel
parameter_list|(
name|String
name|entry
parameter_list|,
name|Collection
argument_list|<
name|Citation
argument_list|>
name|citationColl
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|this
operator|.
name|citationList
operator|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
expr_stmt|;
name|citationList
operator|.
name|setAll
argument_list|(
name|citationColl
argument_list|)
expr_stmt|;
block|}
DECL|method|getCitationList ()
specifier|public
name|ObservableList
argument_list|<
name|Citation
argument_list|>
name|getCitationList
parameter_list|()
block|{
return|return
operator|new
name|ReadOnlyListWrapper
argument_list|<>
argument_list|(
name|citationList
argument_list|)
return|;
block|}
comment|/**      * Return a string for displaying an entry key and its number of uses.      */
DECL|method|getDisplayText ()
specifier|public
name|String
name|getDisplayText
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s (%s)"
argument_list|,
name|entry
argument_list|,
name|citationList
operator|.
name|size
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|new
name|StringJoiner
argument_list|(
literal|", "
argument_list|,
name|ReferenceViewModel
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"["
argument_list|,
literal|"]"
argument_list|)
operator|.
name|add
argument_list|(
literal|"entry='"
operator|+
name|entry
operator|+
literal|"'"
argument_list|)
operator|.
name|add
argument_list|(
literal|"citationList="
operator|+
name|citationList
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

