begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_class
DECL|class|IncrementalSearcher
specifier|public
class|class
name|IncrementalSearcher
block|{
DECL|field|prefs
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|hitInField
specifier|private
name|String
name|hitInField
decl_stmt|;
DECL|method|IncrementalSearcher (JabRefPreferences prefs)
specifier|public
name|IncrementalSearcher
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
block|}
DECL|method|getField ()
specifier|public
name|String
name|getField
parameter_list|()
block|{
return|return
name|hitInField
return|;
block|}
DECL|method|search (String pattern, BibtexEntry bibtexEntry)
specifier|public
name|boolean
name|search
parameter_list|(
name|String
name|pattern
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
name|hitInField
operator|=
literal|null
expr_stmt|;
comment|//if (!prefs.getBoolean("caseSensitiveSearch"))
comment|//    flags = Pattern.CASE_INSENSITIVE;
comment|//Pattern pattern = Pattern.compile(searchString, flags);
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchAll"
argument_list|)
condition|)
block|{
name|Object
index|[]
name|fields
init|=
name|bibtexEntry
operator|.
name|getAllFields
argument_list|()
decl_stmt|;
return|return
name|searchFields
argument_list|(
name|fields
argument_list|,
name|bibtexEntry
argument_list|,
name|pattern
argument_list|)
return|;
block|}
else|else
block|{
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchReq"
argument_list|)
condition|)
block|{
name|String
index|[]
name|requiredField
init|=
name|bibtexEntry
operator|.
name|getRequiredFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|searchFields
argument_list|(
name|requiredField
argument_list|,
name|bibtexEntry
argument_list|,
name|pattern
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchOpt"
argument_list|)
condition|)
block|{
name|String
index|[]
name|optionalField
init|=
name|bibtexEntry
operator|.
name|getOptionalFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|searchFields
argument_list|(
name|optionalField
argument_list|,
name|bibtexEntry
argument_list|,
name|pattern
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchGen"
argument_list|)
condition|)
block|{
name|String
index|[]
name|generalField
init|=
name|bibtexEntry
operator|.
name|getGeneralFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|searchFields
argument_list|(
name|generalField
argument_list|,
name|bibtexEntry
argument_list|,
name|pattern
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
DECL|method|searchFields (Object[] fields, BibtexEntry bibtexEntry, String searchString)
specifier|protected
name|boolean
name|searchFields
parameter_list|(
name|Object
index|[]
name|fields
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|,
name|String
name|searchString
parameter_list|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|fields
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
comment|/*Globals.logger("Searching field '"+fields[i].toString() 				       +"' for '" 				       +pattern.toString()+"'.");*/
if|if
condition|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|fields
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|)
condition|)
block|{
if|if
condition|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|fields
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|indexOf
argument_list|(
name|searchString
argument_list|)
operator|>
operator|-
literal|1
condition|)
name|found
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|fields
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|indexOf
argument_list|(
name|searchString
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|>
operator|-
literal|1
condition|)
name|found
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|found
condition|)
block|{
name|hitInField
operator|=
name|fields
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Searching error: "
operator|+
name|t
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

