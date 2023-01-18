# SolidityRoleAdapter
Combines the results of slither and solcverify in a new output file.
Input files must be located in the data folder to be found by the adapter.

A 'RoleAnnotation.txt',
a 'SolcVerifyResults.txt',
and all available 'SlitherResults<< - classname>>.txt' files must be provided.

To execute the Parsers, simply run the SolidityRoleAdapter ('as Java Aplication').
Parsed Values are written to the console for an overview, which can be disabled by setting PRINT_CONSOLE_INFO to false.

The result is written to the 'SolidityRoleAdapter - Results.txt' file.
