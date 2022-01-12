@Configuration
AppConfig       ->      Factory to create DB Connection.

@Repository
TaskRepository  ->      For storing task entity, should not have business logic.

@Service
TaskService     ->      Operates on the repository, business logic goes here.

@Controller
TaskController  ->      Data collection, accepts user inputs.