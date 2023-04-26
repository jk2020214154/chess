import ModelUser from './user'

export default{
    state: {
        socket: null,
        fen: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
        from: "",
        to: "",
        pos:"",
        status: "ungameing",
        result: "",
    },
    getters:{

    },
    mutations:{
        updateSocket(state,socket){
            state.socket=socket;
        },
        updateFen(state,fen){
            state.fen=fen;
        },
        updateFrom(state,from){
            state.from=from;
        },
        updateTo(state,to){
            state.to=to;
        },
        updateStatus(state,status){
            state.status=status;
        },
        updateResult(state,result){
            state.result=result;
        }
    },
    actions:{

    },
    modules:{
        user: ModelUser,
    }
}