import SwiftUI
import shared

func greet() -> String {
    NSLog("hello from the print function");
    let viewmodel = RestaurantViewModel()
    viewmodel.restaurants().addObserver { (data : NSArray? ) -> Void in
        if data != nil{
            NSLog("Something To Print");
            NSLog(String(data?.count ?? 0));
            if (data!.count > 0){
                let rest = data?.object(at: 1) as! Restaurant
                NSLog("this is the name of the first restaurant")
                NSLog(rest.name)
            }
        }
   }
    viewmodel.getListOfRestaurants()
    return Greeting().greeting()
}

struct ContentView: View {
    var body: some View {
        RestaurantList()
    }
}

struct RestaurantList: View {
    @ObservedObject var fetch = FetchRestaurant()
    var body: some View {
        List(fetch.restaurants, id: \.uid) { rest in
            HStack{
                CustomImageView(urlString: rest.logo)
                VStack{
                    Text(rest.name).fontWeight(.bold)
                    if #available(iOS 14.0, *) {
                        Text(rest.description_).fontWeight(.light).font(.caption2)
                    } else {
                        Text(rest.description_).fontWeight(.light)
                    }
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

class FetchRestaurant: ObservableObject {
    @Published var restaurants = [Restaurant]()
    init() {
        let viewModel = RestaurantViewModel()
        viewModel.restaurants().addObserver { (data : NSArray? ) -> Void in
            if data != nil{
                NSLog("Something To Print");
                NSLog(String(data?.count ?? 0));
                if (data!.count > 0){
                    self.restaurants = data!.compactMap({ $0 as? Restaurant })
                }
            }
       }
        viewModel.getListOfRestaurants()
    }
}

class ImageLoaderService: ObservableObject {
    @Published var image: UIImage = UIImage()
    
    func loadImage(for urlString: String) {
        guard let url = URL(string: urlString) else { return }
        
        let task = URLSession.shared.dataTask(with: url) { data, response, error in
            guard let data = data else { return }
            DispatchQueue.main.async {
                self.image = UIImage(data: data) ?? UIImage()
            }
        }
        task.resume()
    }
    
}
struct CustomImageView: View {
    var urlString: String
    @ObservedObject var imageLoader = ImageLoaderService()
    @State var image: UIImage = UIImage()
    
    var body: some View {
        Image(uiImage: image)
            .resizable()
            .aspectRatio(contentMode: .fit)
            .frame(width:100, height:100)
            .onReceive(imageLoader.$image) { image in
                self.image = image
            }
            .onAppear {
                imageLoader.loadImage(for: urlString)
            }
    }
}
