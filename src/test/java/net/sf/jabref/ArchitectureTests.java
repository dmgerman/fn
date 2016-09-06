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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
import|;
end_import

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|ArchitectureTests
specifier|public
class|class
name|ArchitectureTests
block|{
DECL|field|PACKAGE_JAVAX_SWING
specifier|public
specifier|static
specifier|final
name|String
name|PACKAGE_JAVAX_SWING
init|=
literal|"javax.swing"
decl_stmt|;
DECL|field|PACKAGE_JAVA_AWT
specifier|public
specifier|static
specifier|final
name|String
name|PACKAGE_JAVA_AWT
init|=
literal|"java.awt"
decl_stmt|;
DECL|field|PACKAGE_NET_SF_JABREF_GUI
specifier|public
specifier|static
specifier|final
name|String
name|PACKAGE_NET_SF_JABREF_GUI
init|=
literal|"net.sf.jabref.gui"
decl_stmt|;
DECL|field|PACKAGE_NET_SF_JABREF_LOGIC
specifier|public
specifier|static
specifier|final
name|String
name|PACKAGE_NET_SF_JABREF_LOGIC
init|=
literal|"net.sf.jabref.logic"
decl_stmt|;
DECL|field|PACKAGE_NET_SF_JABREF_MODEL
specifier|public
specifier|static
specifier|final
name|String
name|PACKAGE_NET_SF_JABREF_MODEL
init|=
literal|"net.sf.jabref.model"
decl_stmt|;
DECL|field|firstPackage
specifier|private
specifier|final
name|String
name|firstPackage
decl_stmt|;
DECL|field|secondPackage
specifier|private
specifier|final
name|String
name|secondPackage
decl_stmt|;
DECL|method|ArchitectureTests (String firstPackage, String secondPackage)
specifier|public
name|ArchitectureTests
parameter_list|(
name|String
name|firstPackage
parameter_list|,
name|String
name|secondPackage
parameter_list|)
block|{
name|this
operator|.
name|firstPackage
operator|=
name|firstPackage
expr_stmt|;
name|this
operator|.
name|secondPackage
operator|=
name|secondPackage
expr_stmt|;
block|}
annotation|@
name|Parameterized
operator|.
name|Parameters
argument_list|(
name|name
operator|=
literal|"{index} -- is {0} independent of {1}?"
argument_list|)
DECL|method|data ()
specifier|public
specifier|static
name|Iterable
argument_list|<
name|Object
index|[]
argument_list|>
name|data
parameter_list|()
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|Object
index|[]
index|[]
block|{
block|{
name|PACKAGE_NET_SF_JABREF_LOGIC
block|,
name|PACKAGE_JAVA_AWT
block|}
block|,
block|{
name|PACKAGE_NET_SF_JABREF_LOGIC
block|,
name|PACKAGE_JAVAX_SWING
block|}
block|,
block|{
name|PACKAGE_NET_SF_JABREF_LOGIC
block|,
name|PACKAGE_NET_SF_JABREF_GUI
block|}
block|,
block|{
name|PACKAGE_NET_SF_JABREF_MODEL
block|,
name|PACKAGE_JAVA_AWT
block|}
block|,
block|{
name|PACKAGE_NET_SF_JABREF_MODEL
block|,
name|PACKAGE_JAVAX_SWING
block|}
block|,
block|{
name|PACKAGE_NET_SF_JABREF_MODEL
block|,
name|PACKAGE_NET_SF_JABREF_GUI
block|}
block|,
block|{
name|PACKAGE_NET_SF_JABREF_MODEL
block|,
name|PACKAGE_NET_SF_JABREF_LOGIC
block|}
block|}
argument_list|)
return|;
block|}
annotation|@
name|Test
DECL|method|testLogicIndependentOfSwingAndGui ()
specifier|public
name|void
name|testLogicIndependentOfSwingAndGui
parameter_list|()
throws|throws
name|IOException
block|{
name|assertIndependenceOfPackages
argument_list|()
expr_stmt|;
block|}
DECL|method|assertIndependenceOfPackages ()
specifier|private
name|void
name|assertIndependenceOfPackages
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|files
init|=
name|Files
operator|.
name|walk
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"src"
argument_list|)
argument_list|)
operator|.
name|filter
argument_list|(
name|p
lambda|->
name|p
operator|.
name|toString
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".java"
argument_list|)
argument_list|)
operator|.
name|filter
argument_list|(
name|p
lambda|->
block|{
lambda|try
block|{
return|return
name|Files
operator|.
name|readAllLines
argument_list|(
name|p
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|s
lambda|->
name|s
operator|.
name|startsWith
argument_list|(
literal|"package "
operator|+
name|firstPackage
argument_list|)
argument_list|)
operator|.
name|findAny
argument_list|()
operator|.
name|isPresent
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
block|)
operator|.
name|filter
argument_list|(
name|p
lambda|->
block|{
try|try
block|{
return|return
name|Files
operator|.
name|readAllLines
argument_list|(
name|p
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|s
lambda|->
name|s
operator|.
name|startsWith
argument_list|(
literal|"import "
operator|+
name|secondPackage
argument_list|)
argument_list|)
operator|.
name|findAny
argument_list|()
operator|.
name|isPresent
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
expr_stmt|;
end_class

begin_if
if|if
condition|(
operator|!
name|files
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
name|files
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
end_if

unit|}  }
end_unit

