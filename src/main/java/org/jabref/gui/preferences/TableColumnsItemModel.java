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
name|java
operator|.
name|util
operator|.
name|Objects
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
name|DoubleProperty
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
name|ObjectProperty
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
name|ReadOnlyBooleanProperty
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
name|SimpleDoubleProperty
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
name|SimpleObjectProperty
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

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|value
operator|.
name|ObservableValue
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
name|maintable
operator|.
name|ColumnPreferences
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
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|Field
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
name|entry
operator|.
name|field
operator|.
name|UnknownField
import|;
end_import

begin_class
DECL|class|TableColumnsItemModel
specifier|public
class|class
name|TableColumnsItemModel
block|{
DECL|field|field
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|Field
argument_list|>
name|field
decl_stmt|;
DECL|field|name
specifier|private
specifier|final
name|StringProperty
name|name
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|length
specifier|private
specifier|final
name|DoubleProperty
name|length
init|=
operator|new
name|SimpleDoubleProperty
argument_list|(
name|ColumnPreferences
operator|.
name|DEFAULT_FIELD_LENGTH
argument_list|)
decl_stmt|;
DECL|field|editableProperty
specifier|private
specifier|final
name|BooleanProperty
name|editableProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|true
argument_list|)
decl_stmt|;
DECL|method|TableColumnsItemModel ()
specifier|public
name|TableColumnsItemModel
parameter_list|()
block|{
name|this
operator|.
name|field
operator|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|(
operator|new
name|UnknownField
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"New column"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|TableColumnsItemModel (Field field)
specifier|public
name|TableColumnsItemModel
parameter_list|(
name|Field
name|field
parameter_list|)
block|{
name|this
operator|.
name|field
operator|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|this
operator|.
name|editableProperty
operator|.
name|setValue
argument_list|(
name|this
operator|.
name|field
operator|.
name|get
argument_list|()
operator|instanceof
name|UnknownField
argument_list|)
expr_stmt|;
block|}
DECL|method|TableColumnsItemModel (Field field, double length)
specifier|public
name|TableColumnsItemModel
parameter_list|(
name|Field
name|field
parameter_list|,
name|double
name|length
parameter_list|)
block|{
name|this
operator|.
name|field
operator|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|this
operator|.
name|length
operator|.
name|setValue
argument_list|(
name|length
argument_list|)
expr_stmt|;
name|this
operator|.
name|editableProperty
operator|.
name|setValue
argument_list|(
name|this
operator|.
name|field
operator|.
name|get
argument_list|()
operator|instanceof
name|UnknownField
argument_list|)
expr_stmt|;
block|}
DECL|method|setField (Field field)
specifier|public
name|void
name|setField
parameter_list|(
name|Field
name|field
parameter_list|)
block|{
name|this
operator|.
name|field
operator|.
name|set
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
DECL|method|getField ()
specifier|public
name|Field
name|getField
parameter_list|()
block|{
return|return
name|field
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|fieldProperty ()
specifier|public
name|ObservableValue
argument_list|<
name|Field
argument_list|>
name|fieldProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|field
return|;
block|}
DECL|method|setName (String name)
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|editableProperty
operator|.
name|get
argument_list|()
condition|)
block|{
name|field
operator|.
name|setValue
argument_list|(
operator|new
name|UnknownField
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|field
operator|.
name|get
argument_list|()
operator|.
name|getName
argument_list|()
return|;
block|}
DECL|method|nameProperty ()
specifier|public
name|StringProperty
name|nameProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|name
return|;
block|}
DECL|method|setLength (double length)
specifier|public
name|void
name|setLength
parameter_list|(
name|double
name|length
parameter_list|)
block|{
name|this
operator|.
name|length
operator|.
name|set
argument_list|(
name|length
argument_list|)
expr_stmt|;
block|}
DECL|method|getLength ()
specifier|public
name|double
name|getLength
parameter_list|()
block|{
return|return
name|length
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|lengthProperty ()
specifier|public
name|DoubleProperty
name|lengthProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|length
return|;
block|}
DECL|method|editableProperty ()
specifier|public
name|ReadOnlyBooleanProperty
name|editableProperty
parameter_list|()
block|{
return|return
name|editableProperty
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|field
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|obj
operator|instanceof
name|TableColumnsItemModel
condition|)
block|{
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|field
argument_list|,
operator|(
operator|(
name|TableColumnsItemModel
operator|)
name|obj
operator|)
operator|.
name|field
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

