#include <string>
#include <string.h>
#include <iostream>
#include <nshade_shared_memory.h>
#include <nshade_shared_memory_connection.h>

using namespace std;

int main(int argc, char* argv[]){
	NshadeSharedMemory shm;
	NshadeSharedMemoryConnection* shmc = shm.AddConnection();
	int totalLen=0;
	for (int i=1; i<argc; i++)
		totalLen+=strlen(argv[i])+1;
	char* command = (char *) malloc(totalLen*sizeof(char));
	int pos=0;
	for(int i=1;i<argc;i++){
		strcpy(command+pos,argv[i]);
		pos+=strlen(argv[i]);
		command[pos++]=' ';
	}
	command[pos-1]='\0';
	cout << command << endl;
	shmc->Write(string(command));
    shm.DirtyAll();
    shm.RemoveConnection( shmc );
    shmc = NULL;
	free(command);
}
