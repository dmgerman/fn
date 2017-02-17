begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|EnumSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_comment
comment|/**  * Class for keeping properties of a single BibTeX/biblatex field  */
end_comment

begin_class
DECL|class|BibtexSingleField
specifier|public
class|class
name|BibtexSingleField
block|{
comment|// some field constants
DECL|field|DEFAULT_FIELD_WEIGHT
specifier|public
specifier|static
specifier|final
name|double
name|DEFAULT_FIELD_WEIGHT
init|=
literal|1
decl_stmt|;
DECL|field|MAX_FIELD_WEIGHT
specifier|public
specifier|static
specifier|final
name|double
name|MAX_FIELD_WEIGHT
init|=
literal|2
decl_stmt|;
DECL|field|SMALL_W
specifier|public
specifier|static
specifier|final
name|double
name|SMALL_W
init|=
literal|0.30
decl_stmt|;
DECL|field|MEDIUM_W
specifier|public
specifier|static
specifier|final
name|double
name|MEDIUM_W
init|=
literal|0.5
decl_stmt|;
DECL|field|LARGE_W
specifier|public
specifier|static
specifier|final
name|double
name|LARGE_W
init|=
literal|1.5
decl_stmt|;
DECL|field|DEFAULT_FIELD_LENGTH
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_FIELD_LENGTH
init|=
literal|100
decl_stmt|;
DECL|enum|Flag
specifier|private
enum|enum
name|Flag
block|{
DECL|enumConstant|STANDARD
name|STANDARD
block|,
DECL|enumConstant|PRIVATE
name|PRIVATE
block|,
DECL|enumConstant|DISPLAYABLE
name|DISPLAYABLE
block|,
DECL|enumConstant|WRITEABLE
name|WRITEABLE
block|}
comment|// the field name
DECL|field|name
specifier|private
name|String
name|name
decl_stmt|;
comment|// contains the standard, private, displayable, writable infos
comment|// default is: not standard, public, displayable and writable
DECL|field|flags
specifier|private
specifier|final
name|Set
argument_list|<
name|Flag
argument_list|>
name|flags
init|=
name|EnumSet
operator|.
name|of
argument_list|(
name|Flag
operator|.
name|DISPLAYABLE
argument_list|,
name|Flag
operator|.
name|WRITEABLE
argument_list|)
decl_stmt|;
DECL|field|length
specifier|private
name|int
name|length
init|=
name|DEFAULT_FIELD_LENGTH
decl_stmt|;
DECL|field|weight
specifier|private
name|double
name|weight
init|=
name|DEFAULT_FIELD_WEIGHT
decl_stmt|;
comment|// properties contains a set of FieldProperty to e.g. tell the EntryEditor to add a specific
comment|// function to this field, to format names, or to control the integrity checks.
DECL|field|properties
specifier|private
name|Set
argument_list|<
name|FieldProperty
argument_list|>
name|properties
init|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|FieldProperty
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// a comma separated list of alternative bibtex-fieldnames, e.g.
comment|// "LCCN" is the same like "lib-congress"
comment|// private String otherNames = null ;
DECL|method|BibtexSingleField (String fieldName, boolean pStandard)
specifier|public
name|BibtexSingleField
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|boolean
name|pStandard
parameter_list|)
block|{
name|name
operator|=
name|fieldName
expr_stmt|;
name|setFlag
argument_list|(
name|pStandard
argument_list|,
name|Flag
operator|.
name|STANDARD
argument_list|)
expr_stmt|;
block|}
DECL|method|BibtexSingleField (String fieldName, boolean pStandard, double pWeight)
specifier|public
name|BibtexSingleField
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|boolean
name|pStandard
parameter_list|,
name|double
name|pWeight
parameter_list|)
block|{
name|name
operator|=
name|fieldName
expr_stmt|;
name|setFlag
argument_list|(
name|pStandard
argument_list|,
name|Flag
operator|.
name|STANDARD
argument_list|)
expr_stmt|;
name|weight
operator|=
name|pWeight
expr_stmt|;
block|}
DECL|method|BibtexSingleField (String fieldName, boolean pStandard, int pLength)
specifier|public
name|BibtexSingleField
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|boolean
name|pStandard
parameter_list|,
name|int
name|pLength
parameter_list|)
block|{
name|name
operator|=
name|fieldName
expr_stmt|;
name|setFlag
argument_list|(
name|pStandard
argument_list|,
name|Flag
operator|.
name|STANDARD
argument_list|)
expr_stmt|;
name|length
operator|=
name|pLength
expr_stmt|;
block|}
DECL|method|BibtexSingleField (String fieldName, boolean pStandard, double pWeight, int pLength)
specifier|public
name|BibtexSingleField
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|boolean
name|pStandard
parameter_list|,
name|double
name|pWeight
parameter_list|,
name|int
name|pLength
parameter_list|)
block|{
name|name
operator|=
name|fieldName
expr_stmt|;
name|setFlag
argument_list|(
name|pStandard
argument_list|,
name|Flag
operator|.
name|STANDARD
argument_list|)
expr_stmt|;
name|weight
operator|=
name|pWeight
expr_stmt|;
name|length
operator|=
name|pLength
expr_stmt|;
block|}
comment|/**      * Sets or onsets the given flag      * @param setToOn if true, set the flag; if false, unset the flat      * @param flagID, the id of the flag      */
DECL|method|setFlag (boolean setToOn, Flag flagID)
specifier|private
name|void
name|setFlag
parameter_list|(
name|boolean
name|setToOn
parameter_list|,
name|Flag
name|flagID
parameter_list|)
block|{
if|if
condition|(
name|setToOn
condition|)
block|{
comment|// set the flag
name|flags
operator|.
name|add
argument_list|(
name|flagID
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// unset the flag
name|flags
operator|.
name|remove
argument_list|(
name|flagID
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|isStandard ()
specifier|public
name|boolean
name|isStandard
parameter_list|()
block|{
return|return
name|flags
operator|.
name|contains
argument_list|(
name|Flag
operator|.
name|STANDARD
argument_list|)
return|;
block|}
DECL|method|setPrivate ()
specifier|public
name|void
name|setPrivate
parameter_list|()
block|{
name|flags
operator|.
name|add
argument_list|(
name|Flag
operator|.
name|PRIVATE
argument_list|)
expr_stmt|;
block|}
DECL|method|setPublic ()
specifier|public
name|void
name|setPublic
parameter_list|()
block|{
name|flags
operator|.
name|remove
argument_list|(
name|Flag
operator|.
name|PRIVATE
argument_list|)
expr_stmt|;
block|}
DECL|method|isPrivate ()
specifier|public
name|boolean
name|isPrivate
parameter_list|()
block|{
return|return
name|flags
operator|.
name|contains
argument_list|(
name|Flag
operator|.
name|PRIVATE
argument_list|)
return|;
block|}
DECL|method|setDisplayable (boolean value)
specifier|public
name|void
name|setDisplayable
parameter_list|(
name|boolean
name|value
parameter_list|)
block|{
name|setFlag
argument_list|(
name|value
argument_list|,
name|Flag
operator|.
name|DISPLAYABLE
argument_list|)
expr_stmt|;
block|}
DECL|method|isDisplayable ()
specifier|public
name|boolean
name|isDisplayable
parameter_list|()
block|{
return|return
name|flags
operator|.
name|contains
argument_list|(
name|Flag
operator|.
name|DISPLAYABLE
argument_list|)
return|;
block|}
DECL|method|setWriteable (boolean value)
specifier|public
name|void
name|setWriteable
parameter_list|(
name|boolean
name|value
parameter_list|)
block|{
name|setFlag
argument_list|(
name|value
argument_list|,
name|Flag
operator|.
name|WRITEABLE
argument_list|)
expr_stmt|;
block|}
DECL|method|isWriteable ()
specifier|public
name|boolean
name|isWriteable
parameter_list|()
block|{
return|return
name|flags
operator|.
name|contains
argument_list|(
name|Flag
operator|.
name|WRITEABLE
argument_list|)
return|;
block|}
DECL|method|setExtras (Set<FieldProperty> pExtras)
specifier|public
name|void
name|setExtras
parameter_list|(
name|Set
argument_list|<
name|FieldProperty
argument_list|>
name|pExtras
parameter_list|)
block|{
name|properties
operator|=
name|pExtras
expr_stmt|;
block|}
comment|// fieldExtras contains mappings to tell the EntryEditor to add a specific
comment|// function to this field, for instance a "browse" button for the "pdf" field.
DECL|method|getFieldProperties ()
specifier|public
name|Set
argument_list|<
name|FieldProperty
argument_list|>
name|getFieldProperties
parameter_list|()
block|{
return|return
name|properties
return|;
block|}
DECL|method|setWeight (double value)
specifier|public
name|void
name|setWeight
parameter_list|(
name|double
name|value
parameter_list|)
block|{
name|this
operator|.
name|weight
operator|=
name|value
expr_stmt|;
block|}
DECL|method|getWeight ()
specifier|public
name|double
name|getWeight
parameter_list|()
block|{
return|return
name|this
operator|.
name|weight
return|;
block|}
comment|/**      * @return The maximum (expected) length of the field value;<em>not</em> the length of the field name      */
DECL|method|getLength ()
specifier|public
name|int
name|getLength
parameter_list|()
block|{
return|return
name|this
operator|.
name|length
return|;
block|}
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/**      * Set this field's numeric property      *      * @param numeric true to indicate that this is a numeric field.      * @return this BibtexSingleField instance. Makes it easier to call this      * method on the fly while initializing without using a local variable.      */
DECL|method|setNumeric (boolean numeric)
specifier|public
name|BibtexSingleField
name|setNumeric
parameter_list|(
name|boolean
name|numeric
parameter_list|)
block|{
if|if
condition|(
name|numeric
condition|)
block|{
name|properties
operator|.
name|add
argument_list|(
name|FieldProperty
operator|.
name|NUMERIC
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|properties
operator|.
name|remove
argument_list|(
name|FieldProperty
operator|.
name|NUMERIC
argument_list|)
expr_stmt|;
block|}
return|return
name|this
return|;
block|}
DECL|method|isNumeric ()
specifier|public
name|boolean
name|isNumeric
parameter_list|()
block|{
return|return
name|properties
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|NUMERIC
argument_list|)
return|;
block|}
DECL|method|setName (String fieldName)
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|name
operator|=
name|fieldName
expr_stmt|;
block|}
block|}
end_class

end_unit
