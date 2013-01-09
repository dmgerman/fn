begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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

begin_class
DECL|class|BibtexString
specifier|public
class|class
name|BibtexString
block|{
DECL|enum|Type
specifier|public
enum|enum
name|Type
block|{
DECL|enumConstant|AUTHOR
name|AUTHOR
argument_list|(
literal|"a"
argument_list|)
block|,
DECL|enumConstant|INSTITUTION
name|INSTITUTION
argument_list|(
literal|"i"
argument_list|)
block|,
DECL|enumConstant|PUBLISHER
name|PUBLISHER
argument_list|(
literal|"p"
argument_list|)
block|,
DECL|enumConstant|OTHER
name|OTHER
argument_list|(
literal|""
argument_list|)
block|;
DECL|field|prefix
specifier|private
name|String
name|prefix
decl_stmt|;
DECL|method|Type (String prefix)
name|Type
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
name|this
operator|.
name|prefix
operator|=
name|prefix
expr_stmt|;
block|}
DECL|method|get (String name)
specifier|public
specifier|static
specifier|final
name|Type
name|get
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|name
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
operator|+
literal|""
operator|)
operator|.
name|toUpperCase
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
name|name
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
operator|+
literal|""
operator|)
argument_list|)
condition|)
return|return
name|OTHER
return|;
for|for
control|(
name|Type
name|t
range|:
name|Type
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|t
operator|.
name|prefix
operator|.
name|equals
argument_list|(
name|name
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|+
literal|""
argument_list|)
operator|&&
name|name
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
return|return
name|t
return|;
block|}
return|return
name|OTHER
return|;
block|}
block|}
DECL|field|_name
DECL|field|_content
DECL|field|_id
name|String
name|_name
decl_stmt|,
name|_content
decl_stmt|,
name|_id
decl_stmt|;
DECL|field|_type
name|Type
name|_type
decl_stmt|;
DECL|method|BibtexString (String id, String name, String content)
specifier|public
name|BibtexString
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|_id
operator|=
name|id
expr_stmt|;
name|_name
operator|=
name|name
expr_stmt|;
name|_content
operator|=
name|content
expr_stmt|;
name|_type
operator|=
name|Type
operator|.
name|get
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
DECL|method|BibtexString (String id, String name, String content, Type type)
specifier|public
name|BibtexString
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|content
parameter_list|,
name|Type
name|type
parameter_list|)
block|{
name|_id
operator|=
name|id
expr_stmt|;
name|_name
operator|=
name|name
expr_stmt|;
name|_content
operator|=
name|content
expr_stmt|;
name|_type
operator|=
name|type
expr_stmt|;
block|}
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|_id
return|;
block|}
DECL|method|setId (String id)
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|_id
operator|=
name|id
expr_stmt|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|_name
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
name|_name
operator|=
name|name
expr_stmt|;
name|_type
operator|=
name|Type
operator|.
name|get
argument_list|(
name|name
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|+
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|getContent ()
specifier|public
name|String
name|getContent
parameter_list|()
block|{
return|return
operator|(
operator|(
name|_content
operator|==
literal|null
operator|)
condition|?
literal|""
else|:
name|_content
operator|)
return|;
block|}
DECL|method|setContent (String content)
specifier|public
name|void
name|setContent
parameter_list|(
name|String
name|content
parameter_list|)
block|{
name|_content
operator|=
name|content
expr_stmt|;
block|}
DECL|method|clone ()
specifier|public
name|Object
name|clone
parameter_list|()
block|{
return|return
operator|new
name|BibtexString
argument_list|(
name|_id
argument_list|,
name|_name
argument_list|,
name|_content
argument_list|)
return|;
block|}
DECL|method|getType ()
specifier|public
name|Type
name|getType
parameter_list|()
block|{
return|return
name|_type
return|;
block|}
block|}
end_class

end_unit

