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

begin_comment
comment|/**  * This class is used to represent customized entry types.  *  */
end_comment

begin_class
DECL|class|CustomEntryType
specifier|public
class|class
name|CustomEntryType
extends|extends
name|BibtexEntryType
block|{
DECL|field|name
specifier|private
name|String
name|name
decl_stmt|;
DECL|field|req
DECL|field|opt
specifier|private
name|String
index|[]
name|req
decl_stmt|,
name|opt
decl_stmt|;
DECL|method|CustomEntryType (String name_, String[] req_, String[] opt_)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name_
parameter_list|,
name|String
index|[]
name|req_
parameter_list|,
name|String
index|[]
name|opt_
parameter_list|)
block|{
name|name
operator|=
name|name_
expr_stmt|;
name|req
operator|=
name|req_
expr_stmt|;
name|opt
operator|=
name|opt_
expr_stmt|;
block|}
DECL|method|CustomEntryType (String name_, String reqStr, String optStr)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name_
parameter_list|,
name|String
name|reqStr
parameter_list|,
name|String
name|optStr
parameter_list|)
block|{
name|name
operator|=
name|name_
expr_stmt|;
name|req
operator|=
name|reqStr
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
name|opt
operator|=
name|optStr
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
DECL|method|getOptionalFields ()
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
name|opt
return|;
block|}
DECL|method|getRequiredFields ()
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
name|req
return|;
block|}
DECL|method|describeRequiredFields ()
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|req
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|req
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
operator|(
operator|(
name|i
operator|<=
name|req
operator|.
name|length
operator|-
literal|1
operator|)
operator|&&
operator|(
name|req
operator|.
name|length
operator|>
literal|1
operator|)
operator|)
condition|?
literal|", "
else|:
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|describeOptionalFields ()
specifier|public
name|String
name|describeOptionalFields
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|opt
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|opt
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
operator|(
operator|(
name|i
operator|<=
name|opt
operator|.
name|length
operator|-
literal|1
operator|)
operator|&&
operator|(
name|opt
operator|.
name|length
operator|>
literal|1
operator|)
operator|)
condition|?
literal|", "
else|:
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|hasAllRequiredFields (BibtexEntry entry)
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
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
name|req
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
name|req
index|[
name|i
index|]
argument_list|)
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

