begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref.imports
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Constructor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
import|;
end_import

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
name|Arrays
import|;
end_import

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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|junit
operator|.
name|framework
operator|.
name|TestCase
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexDatabase
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|FindUnlinkedFilesDialog
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|FindUnlinkedFilesDialog
operator|.
name|CheckableTreeNode
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
operator|.
name|ExternalFileType
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|FileListEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|FileListTableModel
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|BibtexParser
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|EntryFromPDFCreator
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|ParserResult
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|UnlinkedFilesCrawler
import|;
end_import

begin_comment
comment|/**  *   * @author Nosh&Dan  * @version 09.11.2008 | 21:06:17  *   */
end_comment

begin_class
DECL|class|DatabaseFileLookupTest
specifier|public
class|class
name|DatabaseFileLookupTest
extends|extends
name|TestCase
block|{
DECL|field|database
specifier|private
name|BibtexDatabase
name|database
decl_stmt|;
DECL|field|entries
specifier|private
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
decl_stmt|;
DECL|field|entry1
specifier|private
name|BibtexEntry
name|entry1
decl_stmt|;
DECL|field|entry2
specifier|private
name|BibtexEntry
name|entry2
decl_stmt|;
DECL|field|pdfDirectory
specifier|private
name|File
name|pdfDirectory
decl_stmt|;
DECL|field|fileInDatabase
specifier|private
name|File
name|fileInDatabase
decl_stmt|;
DECL|field|fileNotInDatabase
specifier|private
name|File
name|fileNotInDatabase
decl_stmt|;
comment|/* (non-Javadoc) 	 * @see junit.framework.TestCase#setUp() 	 */
DECL|method|setUp ()
specifier|protected
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|super
operator|.
name|setUp
argument_list|()
expr_stmt|;
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|FileReader
argument_list|(
literal|"src/tests/net/sf/jabref/util/unlinkedFilesTestBib.bib"
argument_list|)
argument_list|)
decl_stmt|;
name|database
operator|=
name|result
operator|.
name|getDatabase
argument_list|()
expr_stmt|;
name|entries
operator|=
name|database
operator|.
name|getEntries
argument_list|()
expr_stmt|;
name|entry1
operator|=
name|database
operator|.
name|getEntryByKey
argument_list|(
literal|"entry1"
argument_list|)
expr_stmt|;
name|entry2
operator|=
name|database
operator|.
name|getEntryByKey
argument_list|(
literal|"entry2"
argument_list|)
expr_stmt|;
name|pdfDirectory
operator|=
operator|new
name|File
argument_list|(
literal|"src/tests/net/sf/jabref/imports/unlinkedFilesTestFolder"
argument_list|)
expr_stmt|;
name|fileInDatabase
operator|=
operator|new
name|File
argument_list|(
name|pdfDirectory
operator|.
name|getPath
argument_list|()
operator|+
name|File
operator|.
name|separator
operator|+
literal|"pdfInDatabase.pdf"
argument_list|)
expr_stmt|;
name|fileNotInDatabase
operator|=
operator|new
name|File
argument_list|(
name|pdfDirectory
operator|.
name|getPath
argument_list|()
operator|+
name|File
operator|.
name|separator
operator|+
literal|"pdfNotInDatabase.pdf"
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Tests the prerequisites of this test-class itself. 	 */
DECL|method|testTestDatabase ()
specifier|public
name|void
name|testTestDatabase
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|database
operator|.
name|getEntryCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|entry1
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|entry2
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|pdfDirectory
operator|.
name|exists
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|pdfDirectory
operator|.
name|isDirectory
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|fileInDatabase
operator|.
name|exists
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|fileInDatabase
operator|.
name|isFile
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|fileNotInDatabase
operator|.
name|exists
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|fileNotInDatabase
operator|.
name|isFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|testInsertTestData ()
specifier|public
name|void
name|testInsertTestData
parameter_list|()
throws|throws
name|Exception
block|{
name|entry1
operator|=
operator|new
name|BibtexEntry
argument_list|()
expr_stmt|;
name|JabRefPreferences
name|jabRefPreferences
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|ExternalFileType
name|fileType
init|=
name|jabRefPreferences
operator|.
name|getExternalFileTypeByExt
argument_list|(
literal|"PDF"
argument_list|)
decl_stmt|;
name|FileListEntry
name|fileListEntry
init|=
operator|new
name|FileListEntry
argument_list|(
literal|""
argument_list|,
name|fileInDatabase
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|fileType
argument_list|)
decl_stmt|;
name|FileListTableModel
name|model
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|model
operator|.
name|addEntry
argument_list|(
literal|0
argument_list|,
name|fileListEntry
argument_list|)
expr_stmt|;
name|entry1
operator|.
name|setField
argument_list|(
literal|"file"
argument_list|,
name|model
operator|.
name|getStringRepresentation
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry1
argument_list|)
expr_stmt|;
comment|// #################### SETUP END ##################### //
name|UnlinkedFilesCrawler
name|crawler
init|=
operator|new
name|UnlinkedFilesCrawler
argument_list|(
name|database
argument_list|)
decl_stmt|;
name|CheckableTreeNode
name|treeNode
init|=
name|crawler
operator|.
name|searchDirectory
argument_list|(
name|pdfDirectory
argument_list|,
operator|new
name|EntryFromPDFCreator
argument_list|()
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|treeNode
argument_list|)
expr_stmt|;
comment|/** 		 * Select all nodes manually. 		 */
name|Enumeration
name|enumeration
init|=
name|treeNode
operator|.
name|breadthFirstEnumeration
argument_list|()
decl_stmt|;
while|while
condition|(
name|enumeration
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|CheckableTreeNode
name|nextElement
init|=
operator|(
name|CheckableTreeNode
operator|)
name|enumeration
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|nextElement
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|File
argument_list|>
name|resultList
init|=
name|getFileListFromNode
argument_list|(
name|treeNode
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|resultList
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|resultList
operator|.
name|contains
argument_list|(
name|fileNotInDatabase
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|resultList
operator|.
name|contains
argument_list|(
name|fileInDatabase
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Connector-Method for the private method 	 * {@link FindUnlinkedFilesDialog#getFileListFromNode()} of the dialog 	 * {@link FindUnlinkedFilesDialog}.<br> 	 *<br> 	 * This method uses<b>reflection</b> to get access to that method. 	 *  	 * @see FindUnlinkedFilesDialog#getFileListFromNode() 	 */
DECL|method|getFileListFromNode (CheckableTreeNode node)
specifier|private
name|List
argument_list|<
name|File
argument_list|>
name|getFileListFromNode
parameter_list|(
name|CheckableTreeNode
name|node
parameter_list|)
throws|throws
name|Exception
block|{
return|return
name|invokeMethod
argument_list|(
literal|"getFileListFromNode"
argument_list|,
name|FindUnlinkedFilesDialog
operator|.
name|class
argument_list|,
name|node
argument_list|)
return|;
block|}
comment|/** 	 * Invokes a method in the supplied class with the given arguments, and 	 * returnes the methods result in the desired type.<br> 	 *<br> 	 * The only requirement ist, that the type, on which the method is to be 	 * called, has the default constructor.<br> 	 *<br> 	 * This method will create an instance of the provided class 	 *<code>targetClass</code>, which is generally described by the generic 	 * parameter<code>T</code> (for<i>Type</i>). The instance is created using 	 * the<b>default constructor</b>. If the default constructor is not 	 * declared, an Exception will be throwen. However, there is no requirement 	 * on the visibility of the default constructor.<br> 	 * Using this instance, the method specified by the string parameter 	 *<code>methodName</code> will be invoked. Again, there is no requirement 	 * on the visibility of the method.<br> 	 * The method will be invoked, using the supplied parameter-list 	 *<code>params</code>.<br> 	 *<br> 	 * The result will be returned as an object of the generic type 	 *<code>R</code> (for<i>Result</i>), and as this type parameter 	 *<code>R</code> is not further specified, the result my be any type and 	 * does not need to be casted. 	 *  	 * @param<R> 	 *            The result type of the method. Does not need to be declared. 	 * @param<T> 	 *            The type, on which the method will be invoked. 	 * @param methodName 	 *            Method name to be invoked. 	 * @param targetClass 	 *            Class instance of the type, on which the method is to be 	 *            invoked. 	 * @param params 	 *            Parameters for the invokation of the method. 	 * @return The result of the method, that is invoked. 	 */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|invokeMethod (String methodName, Class<T> targetClass, Object... params)
specifier|public
specifier|static
parameter_list|<
name|R
parameter_list|,
name|T
parameter_list|>
name|R
name|invokeMethod
parameter_list|(
name|String
name|methodName
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|targetClass
parameter_list|,
name|Object
modifier|...
name|params
parameter_list|)
throws|throws
name|Exception
block|{
name|T
name|instance
init|=
name|getInstanceFromType
argument_list|(
name|targetClass
argument_list|)
decl_stmt|;
if|if
condition|(
name|instance
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|InstantiationException
argument_list|(
literal|"The type '"
operator|+
name|targetClass
operator|+
literal|"' could not be instantiated."
argument_list|)
throw|;
block|}
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|paramTypes
init|=
operator|new
name|Class
argument_list|<
name|?
argument_list|>
index|[
name|params
operator|.
name|length
index|]
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
name|params
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|paramTypes
index|[
name|i
index|]
operator|=
name|params
index|[
name|i
index|]
operator|.
name|getClass
argument_list|()
expr_stmt|;
name|Method
name|method
init|=
name|targetClass
operator|.
name|getDeclaredMethod
argument_list|(
name|methodName
argument_list|,
name|paramTypes
argument_list|)
decl_stmt|;
name|method
operator|.
name|setAccessible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
operator|(
name|R
operator|)
name|method
operator|.
name|invoke
argument_list|(
name|instance
argument_list|,
name|params
argument_list|)
return|;
block|}
DECL|method|getInstanceFromType (Class<T> targetClass)
specifier|private
specifier|static
parameter_list|<
name|T
parameter_list|>
name|T
name|getInstanceFromType
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|targetClass
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|T
name|instance
init|=
literal|null
decl_stmt|;
try|try
block|{
name|Constructor
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|constructor
decl_stmt|;
name|constructor
operator|=
name|targetClass
operator|.
name|getDeclaredConstructor
argument_list|()
expr_stmt|;
name|constructor
operator|.
name|setAccessible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|instance
operator|=
name|constructor
operator|.
name|newInstance
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|instance
operator|=
name|getInstanceFromNonDefaultConstructor
argument_list|(
name|targetClass
argument_list|)
expr_stmt|;
block|}
return|return
name|instance
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|orderByParamCount (Constructor<? extends T>[] constructors)
specifier|private
specifier|static
parameter_list|<
name|T
parameter_list|>
name|Constructor
argument_list|<
name|?
extends|extends
name|T
argument_list|>
index|[]
name|orderByParamCount
parameter_list|(
name|Constructor
argument_list|<
name|?
extends|extends
name|T
argument_list|>
index|[]
name|constructors
parameter_list|)
block|{
name|List
argument_list|<
name|Constructor
argument_list|<
name|?
extends|extends
name|T
argument_list|>
argument_list|>
name|list
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|constructors
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|list
argument_list|,
operator|new
name|Comparator
argument_list|<
name|Constructor
argument_list|<
name|?
extends|extends
name|T
argument_list|>
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Constructor
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|c1
parameter_list|,
name|Constructor
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|c2
parameter_list|)
block|{
return|return
operator|new
name|Integer
argument_list|(
name|c1
operator|.
name|getParameterTypes
argument_list|()
operator|.
name|length
argument_list|)
operator|.
name|compareTo
argument_list|(
name|c2
operator|.
name|getParameterTypes
argument_list|()
operator|.
name|length
argument_list|)
return|;
block|}
block|}
block|)
function|;
return|return
operator|new
name|ArrayList
argument_list|<
name|Constructor
argument_list|<
name|?
extends|extends
name|T
argument_list|>
argument_list|>
argument_list|(
name|list
argument_list|)
operator|.
name|toArray
argument_list|(
operator|new
name|Constructor
index|[
name|list
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
end_class

begin_function
DECL|method|getInstanceFromNonDefaultConstructor (Class<T> targetClass)
specifier|private
specifier|static
parameter_list|<
name|T
parameter_list|>
name|T
name|getInstanceFromNonDefaultConstructor
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|targetClass
parameter_list|)
block|{
name|Constructor
argument_list|<
name|?
argument_list|>
index|[]
name|constructors
init|=
name|targetClass
operator|.
name|getDeclaredConstructors
argument_list|()
decl_stmt|;
name|constructors
operator|=
name|orderByParamCount
argument_list|(
name|constructors
argument_list|)
expr_stmt|;
for|for
control|(
name|Constructor
argument_list|<
name|?
argument_list|>
name|constructor
range|:
name|constructors
control|)
block|{
name|constructor
operator|.
name|setAccessible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|parameterTypes
init|=
name|constructor
operator|.
name|getParameterTypes
argument_list|()
decl_stmt|;
try|try
block|{
comment|/**                  * Trying to invoke constructor with<code>null</code> values.                  */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|T
name|instance
init|=
operator|(
name|T
operator|)
name|constructor
operator|.
name|newInstance
argument_list|(
operator|new
name|Object
index|[
name|parameterTypes
operator|.
name|length
index|]
argument_list|)
decl_stmt|;
return|return
name|instance
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ignored
parameter_list|)
block|{             }
comment|/**              * Creating proper instances for the parameter types.              */
name|Object
index|[]
name|arguments
init|=
name|createArguments
argument_list|(
name|parameterTypes
argument_list|,
name|targetClass
argument_list|)
decl_stmt|;
if|if
condition|(
name|arguments
operator|==
literal|null
condition|)
block|{
continue|continue;
block|}
try|try
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|T
name|instance
init|=
operator|(
name|T
operator|)
name|constructor
operator|.
name|newInstance
argument_list|(
name|arguments
argument_list|)
decl_stmt|;
return|return
name|instance
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
continue|continue;
block|}
block|}
return|return
literal|null
return|;
block|}
end_function

begin_comment
comment|/** 	 * Creates an argument-array for the type-array<code>parameterTypes</code> 	 * by trying to instanciate every single parameter type.<br> 	 *<br> 	 * If one of the instanciation fails, the<code>null</code> value will be written 	 * into the argument-array. 	 *  	 * @param parameterTypes An Array of types, which shall be created. 	 * @return An array of arguments. 	 */
end_comment

begin_function
DECL|method|createArguments (Class<?>[] parameterTypes, Class<T> targetClass)
specifier|private
specifier|static
parameter_list|<
name|T
parameter_list|>
name|Object
index|[]
name|createArguments
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|parameterTypes
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|targetClass
parameter_list|)
block|{
name|Object
index|[]
name|parameterValues
init|=
operator|new
name|Object
index|[
name|parameterTypes
operator|.
name|length
index|]
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
name|parameterTypes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Class
argument_list|<
name|?
argument_list|>
name|typeClass
init|=
name|parameterTypes
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
name|targetClass
operator|.
name|equals
argument_list|(
name|typeClass
argument_list|)
condition|)
block|{
return|return
literal|null
return|;
block|}
try|try
block|{
name|parameterValues
index|[
name|i
index|]
operator|=
name|getInstanceFromType
argument_list|(
name|typeClass
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|parameterValues
index|[
name|i
index|]
operator|=
literal|null
expr_stmt|;
block|}
block|}
return|return
name|parameterValues
return|;
block|}
end_function

unit|}
end_unit

