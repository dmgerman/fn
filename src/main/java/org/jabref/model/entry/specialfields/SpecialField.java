begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.specialfields
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|specialfields
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|KeywordList
import|;
end_import

begin_enum
DECL|enum|SpecialField
specifier|public
enum|enum
name|SpecialField
block|{
DECL|enumConstant|PRINTED
name|PRINTED
argument_list|(
literal|"printed"
argument_list|,
name|SpecialFieldValue
operator|.
name|PRINTED
argument_list|)
block|,
DECL|enumConstant|PRIORITY
name|PRIORITY
argument_list|(
literal|"priority"
argument_list|,
name|SpecialFieldValue
operator|.
name|CLEAR_PRIORITY
argument_list|,
name|SpecialFieldValue
operator|.
name|PRIORITY_HIGH
argument_list|,
name|SpecialFieldValue
operator|.
name|PRIORITY_MEDIUM
argument_list|,
name|SpecialFieldValue
operator|.
name|PRIORITY_LOW
argument_list|)
block|,
DECL|enumConstant|QUALITY
name|QUALITY
argument_list|(
literal|"qualityassured"
argument_list|,
name|SpecialFieldValue
operator|.
name|QUALITY_ASSURED
argument_list|)
block|,
DECL|enumConstant|RANKING
name|RANKING
argument_list|(
literal|"ranking"
argument_list|,
name|SpecialFieldValue
operator|.
name|CLEAR_RANK
argument_list|,
name|SpecialFieldValue
operator|.
name|RANK_1
argument_list|,
name|SpecialFieldValue
operator|.
name|RANK_2
argument_list|,
name|SpecialFieldValue
operator|.
name|RANK_3
argument_list|,
name|SpecialFieldValue
operator|.
name|RANK_4
argument_list|,
name|SpecialFieldValue
operator|.
name|RANK_5
argument_list|)
block|,
DECL|enumConstant|READ_STATUS
name|READ_STATUS
argument_list|(
literal|"readstatus"
argument_list|,
name|SpecialFieldValue
operator|.
name|CLEAR_READ_STATUS
argument_list|,
name|SpecialFieldValue
operator|.
name|READ
argument_list|,
name|SpecialFieldValue
operator|.
name|SKIMMED
argument_list|)
block|,
DECL|enumConstant|RELEVANCE
name|RELEVANCE
argument_list|(
literal|"relevance"
argument_list|,
name|SpecialFieldValue
operator|.
name|RELEVANT
argument_list|)
block|;
DECL|field|values
specifier|private
name|List
argument_list|<
name|SpecialFieldValue
argument_list|>
name|values
decl_stmt|;
DECL|field|keywords
specifier|private
name|KeywordList
name|keywords
decl_stmt|;
DECL|field|map
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|SpecialFieldValue
argument_list|>
name|map
decl_stmt|;
DECL|field|fieldName
specifier|private
name|String
name|fieldName
decl_stmt|;
DECL|method|SpecialField (String fieldName, SpecialFieldValue... values)
name|SpecialField
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|SpecialFieldValue
modifier|...
name|values
parameter_list|)
block|{
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|this
operator|.
name|values
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|this
operator|.
name|keywords
operator|=
operator|new
name|KeywordList
argument_list|()
expr_stmt|;
name|this
operator|.
name|map
operator|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
expr_stmt|;
for|for
control|(
name|SpecialFieldValue
name|value
range|:
name|values
control|)
block|{
name|this
operator|.
name|values
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|value
operator|.
name|getKeyword
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|keywords
operator|::
name|add
argument_list|)
expr_stmt|;
name|value
operator|.
name|getFieldValue
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|fieldValue
lambda|->
name|map
operator|.
name|put
argument_list|(
name|fieldValue
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getValues ()
specifier|public
name|List
argument_list|<
name|SpecialFieldValue
argument_list|>
name|getValues
parameter_list|()
block|{
return|return
name|this
operator|.
name|values
return|;
block|}
DECL|method|getKeyWords ()
specifier|public
name|KeywordList
name|getKeyWords
parameter_list|()
block|{
return|return
name|this
operator|.
name|keywords
return|;
block|}
DECL|method|parse (String value)
specifier|public
name|Optional
argument_list|<
name|SpecialFieldValue
argument_list|>
name|parse
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|map
operator|.
name|get
argument_list|(
name|value
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|fieldName
return|;
block|}
DECL|method|isSingleValueField ()
specifier|public
name|boolean
name|isSingleValueField
parameter_list|()
block|{
return|return
name|this
operator|.
name|values
operator|.
name|size
argument_list|()
operator|==
literal|1
return|;
block|}
DECL|method|getSpecialFieldInstanceFromFieldName (String fieldName)
specifier|public
specifier|static
name|Optional
argument_list|<
name|SpecialField
argument_list|>
name|getSpecialFieldInstanceFromFieldName
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
switch|switch
condition|(
name|fieldName
condition|)
block|{
case|case
literal|"priority"
case|:
return|return
name|Optional
operator|.
name|of
argument_list|(
name|SpecialField
operator|.
name|PRIORITY
argument_list|)
return|;
case|case
literal|"qualityassured"
case|:
return|return
name|Optional
operator|.
name|of
argument_list|(
name|SpecialField
operator|.
name|QUALITY
argument_list|)
return|;
case|case
literal|"ranking"
case|:
return|return
name|Optional
operator|.
name|of
argument_list|(
name|SpecialField
operator|.
name|RANKING
argument_list|)
return|;
case|case
literal|"readstatus"
case|:
return|return
name|Optional
operator|.
name|of
argument_list|(
name|SpecialField
operator|.
name|READ_STATUS
argument_list|)
return|;
case|case
literal|"relevance"
case|:
return|return
name|Optional
operator|.
name|of
argument_list|(
name|SpecialField
operator|.
name|RELEVANCE
argument_list|)
return|;
case|case
literal|"printed"
case|:
return|return
name|Optional
operator|.
name|of
argument_list|(
name|SpecialField
operator|.
name|PRINTED
argument_list|)
return|;
default|default:
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
comment|/**      * @param fieldName the name of the field to check      * @return true if given field is a special field, false otherwise      */
DECL|method|isSpecialField (String fieldName)
specifier|public
specifier|static
name|boolean
name|isSpecialField
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
return|return
name|SpecialField
operator|.
name|getSpecialFieldInstanceFromFieldName
argument_list|(
name|fieldName
argument_list|)
operator|.
name|isPresent
argument_list|()
return|;
block|}
block|}
end_enum

end_unit
