begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

begin_comment
comment|/***  * Operating system (OS) detection  */
end_comment

begin_class
DECL|class|OS
specifier|public
class|class
name|OS
block|{
comment|// https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/SystemUtils.html
DECL|field|OS_NAME
specifier|private
specifier|static
specifier|final
name|String
name|OS_NAME
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.name"
argument_list|,
literal|"unknown"
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
DECL|field|LINUX
specifier|public
specifier|static
specifier|final
name|boolean
name|LINUX
init|=
name|OS_NAME
operator|.
name|startsWith
argument_list|(
literal|"linux"
argument_list|)
decl_stmt|;
DECL|field|WINDOWS
specifier|public
specifier|static
specifier|final
name|boolean
name|WINDOWS
init|=
name|OS_NAME
operator|.
name|startsWith
argument_list|(
literal|"win"
argument_list|)
decl_stmt|;
DECL|field|OS_X
specifier|public
specifier|static
specifier|final
name|boolean
name|OS_X
init|=
name|OS_NAME
operator|.
name|startsWith
argument_list|(
literal|"mac"
argument_list|)
decl_stmt|;
comment|// File separator obtained from system
DECL|field|FILE_SEPARATOR
specifier|public
specifier|static
specifier|final
name|String
name|FILE_SEPARATOR
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
decl_stmt|;
comment|// Newlines
comment|// will be overridden in initialization due to feature #857 @ JabRef.java
DECL|field|NEWLINE
specifier|public
specifier|static
name|String
name|NEWLINE
init|=
name|System
operator|.
name|lineSeparator
argument_list|()
decl_stmt|;
block|}
end_class

end_unit
